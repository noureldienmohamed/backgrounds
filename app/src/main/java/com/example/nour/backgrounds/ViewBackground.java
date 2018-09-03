package com.example.nour.backgrounds;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nour.backgrounds.Common.Common;
import com.example.nour.backgrounds.Database.DataSource.RecentRepository;
import com.example.nour.backgrounds.Database.LocalDatabase.LocalDatabase;
import com.example.nour.backgrounds.Database.LocalDatabase.RecentDataSource;
import com.example.nour.backgrounds.Database.Recents;
import com.example.nour.backgrounds.Helper.SaveImageHelper;
import com.example.nour.backgrounds.Model.BackgroudItem;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import dmax.dialog.SpotsDialog;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ViewBackground extends AppCompatActivity {
CollapsingToolbarLayout collapsingToolbarLayout;
FloatingActionButton floatingActionButton,flatdown;
ImageView imageView;
CoordinatorLayout rootLayout;
Toolbar toolbar;
FloatingActionMenu menuFloating;
//com.github.clans.fab.FloatingActionButton fbshare;
CompositeDisposable compositeDisposable;
RecentRepository recentRepository;
//CallbackManager callbackManager;
//ShareDialog shareDialog;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case Common.PERMISSION_REQUIEST_CODE:
            {
                if (grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AlertDialog dialog = new SpotsDialog(ViewBackground.this);
                    dialog.show();
                    dialog.setMessage("please Waiting ...");
                    String filname = UUID.randomUUID().toString() + ".png";
                    Picasso.with(getBaseContext())
                            .load(Common.select_background.getImageUrl())
                            .into(new SaveImageHelper(getBaseContext(), dialog
                                    , getApplicationContext().getContentResolver(),
                                    filname, "Wallpaper image"));
                } else
                    Toast.makeText(this,"You need accept this permission",Toast.LENGTH_SHORT).show();
            }
            break;

        }


    }
    private Target target = new Target() {
    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext()) ;
    try {
        wallpaperManager.setBitmap(bitmap);
        Snackbar.make(rootLayout,"Wallpaper was Set",Snackbar.LENGTH_SHORT).show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
};
    private Target facebookConvertbitmap = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto sharePhoto = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();
            if (ShareDialog.canShow(SharePhotoContent.class))
            {

                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(sharePhoto)
                        .build();
              //  shareDialog.show(content);
            }
        }
        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    protected void onDestroy() {
        Picasso.with(this).cancelRequest(target);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_background);
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null)
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        //callbackManager=CallbackManager.Factory.create();
       // shareDialog= new ShareDialog(ViewBackground.this);

        compositeDisposable= new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(this);
        recentRepository=RecentRepository.getIntance(RecentDataSource.getInstance(database.recentsDa()));
        rootLayout=(CoordinatorLayout)findViewById(R.id.rootLayout);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.coolapsApp);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.coolapsExp);
        collapsingToolbarLayout.setTitle(Common.Category_selscted);
        imageView=findViewById(R.id.imageThumb);
      Picasso.with(this)
              .load(Common.select_background.getImageUrl())
              .into(imageView);
      menuFloating=(FloatingActionMenu)findViewById(R.id.menu);
     /*fbshare=(com.github.clans.fab.FloatingActionButton)findViewById(R.id.fb_share);
       fbshare.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View v) {
              shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

                  @Override
                  public void onSuccess(Sharer.Result result) {
                      Toast.makeText(ViewBackground.this, "share successful", Toast.LENGTH_SHORT).show();
                  }

                  @Override
                  public void onCancel() {
                      Toast.makeText(ViewBackground.this, "share canceled !", Toast.LENGTH_SHORT).show();

                  }

                  @Override
                  public void onError(FacebookException error) {
                      Toast.makeText(ViewBackground.this, ""+error.getMessage() , Toast.LENGTH_SHORT).show();
                  }
              });
         Picasso.with(getBaseContext())
                 .load(Common.select_background.getImageUrl())
                 .into(facebookConvertbitmap);

          }
      });
*/

      addToRecents();
      floatingActionButton=findViewById(R.id.floating);
      floatingActionButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Picasso.with(getBaseContext())
                      .load(Common.select_background.getImageUrl())
                      .into(target);
          }
      });
    flatdown=findViewById(R.id.floatdownload);
    flatdown.setOnClickListener(new View.OnClickListener() {
        @SuppressLint("NewApi")
        @Override
        public void onClick(View v) {
            if (ActivityCompat.checkSelfPermission(ViewBackground.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Common.PERMISSION_REQUIEST_CODE);
            }
            else
            {
                AlertDialog dialog = new SpotsDialog(ViewBackground.this);
                dialog.show();
                dialog.setMessage("please Waiting ...");
                String filname = UUID.randomUUID().toString()+".png";
                Picasso.with(getBaseContext())
                        .load(Common.select_background.getImageUrl())
                        .into(new SaveImageHelper(getBaseContext(),dialog
                                ,getApplicationContext().getContentResolver(),
                                filname,"Wallpaper image"));
            }
        }
    });
increaseViewCount();

    }

    private void increaseViewCount() {
        FirebaseDatabase.getInstance()
                .getReference(Common.STR_BACKGROUND)
                .child(Common.select_background_key)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("viewCount"))
                        {
                            BackgroudItem backgroudItem = dataSnapshot.getValue(BackgroudItem.class);
                            long count = backgroudItem.getViewCount()+1;
                            Map<String,Object>update_view = new HashMap<>();
                            update_view.put("viewCount",count);
                            FirebaseDatabase.getInstance()
                                    .getReference(Common.STR_BACKGROUND)
                                    .child(Common.select_background_key)
                                    .updateChildren(update_view)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ViewBackground.this, "Cannot update view count", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                        else
                        {
                                                Map<String,Object>update_view = new HashMap<>();
                                                update_view.put("viewCount",Long.valueOf(1));
                                                FirebaseDatabase.getInstance()
                                                        .getReference(Common.STR_BACKGROUND)
                                                        .child(Common.select_background_key)
                                                        .updateChildren(update_view)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(ViewBackground.this, "Cannot set default view count", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void addToRecents() {
     Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>(){
     @Override
     public void subscribe (ObservableEmitter<Object> e) throws Exception {
         Recents recents = new Recents(Common.select_background.getImageUrl(),
                 Common.select_background.getCategoryId(),
                 String.valueOf(System.currentTimeMillis()),
                 Common.select_background_key);
         recentRepository.interRecents(recents);
         e.onComplete();

     }
        }).observeOn(AndroidSchedulers.mainThread())
             .subscribeOn(Schedulers.io())
             .subscribe(new Consumer<Object>() {
                 @Override
                 public void accept(Object o) throws Exception {

                 }
             }, new Consumer<Throwable>() {
                 @Override
                 public void accept(Throwable throwable) throws Exception {
                     Log.e("ERROR", throwable.getMessage());

                 }
             }, new Action() {
                 @Override
                 public void run() throws Exception {

                 }
             });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}