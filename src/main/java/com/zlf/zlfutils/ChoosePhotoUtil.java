package com.zlf.zlfutils;/**
 * Created by Administrator on 2017/6/12 0012.
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：zhaolifeng
 * 时间：2017/06/12 18:35
 * 描述：照片选择封装类
 * <p>
 * 1   ChoosePhotoUtil      choosePhotoUtil = new ChoosePhotoUtil(this, this);
 * 2   实现 FilesReceiving   重写他的2个方法   接受图片Uri  可获取路径和转换成文件 或者bitmap
 *
 * @Override public void receivePicture(Uri uri) {
 * if (null != uri) {
 * File  file=  new File(uri.getPath())
 * }
 * }
 * @Override public void errorMessage(String error) {
 * ToastUtil.showToast(context, error);
 * }
 * 3    在 onActivityResult调用 此类的
 * @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 * super.onActivityResult(requestCode, resultCode, data);
 * choosePhotoUtil.onActivityResult(requestCode, resultCode, data);
 * }
 */
public class ChoosePhotoUtil implements Serializable {

    private final int MAKE_PHOTO = 0x6001;// 拍照

    private final int PHOTO_ALBUM = 0x6002;// 调用相册

    private final int PHOTO_REQUEST_CUT = 0x6003;// 结果

    private final int NULL = 0;// 取消

    private Activity activity;
    private FilesReceiving filesReceiving;
    private PictureCut mPictureCut = null;
    //原图Uri
    private Uri mOriginalUri = null;
    //裁剪以后的Uri
    private Uri mPictureCutUri = null;

    private String TAG = this.getClass().getSimpleName();


    public ChoosePhotoUtil(Activity activity, FilesReceiving filesReceiving) {
        this.activity = activity;
        this.filesReceiving = filesReceiving;
    }

    /**
     * 从相册获取图片
     */
    public void ChoosePhotoFromAlbum() {
        Intent getAlbum = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getAlbum.setType("image/*");
        activity.startActivityForResult(getAlbum, PHOTO_ALBUM);
    }

    /**
     * 从相机获取图片
     */
    public void ChoosePhotoFromCamera() {
        Intent getPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        setOriginalUri(newlyBuild());//创建文件
        getPhoto.putExtra(MediaStore.EXTRA_OUTPUT, getOriginalUri());//根据uri保存照片
        getPhoto.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);//保存照片的质量  0 1
        if (getPhoto.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(getPhoto, MAKE_PHOTO);//启动相机拍照
        }
    }

    /**
     * 返回的Uri
     */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case MAKE_PHOTO:  //相机
                    if (null == getOriginalUri()) {
                        return true;
                    }
                    if (null != mPictureCut) {//是否裁剪
                        setPictureCutUri(newlyCacheBuild());
                        mPictureCut.shearCriterion(new Intent("com.android.camera.action.CROP"),
                                getOriginalUri(), getPictureCutUri(), PHOTO_REQUEST_CUT);
                    } else {
                        filesReceiving.receivePicture(getOriginalUri());
                    }
                    return true;
                case PHOTO_ALBUM:  //相册
                    setOriginalUri(data.getData());
                    if (null == getOriginalUri()) {
                        return true;
                    }
                    if (null != mPictureCut) {  //是否裁剪
                        setPictureCutUri(newlyCacheBuild());
                        mPictureCut.shearCriterion(new Intent("com.android.camera.action.CROP"),
                                getOriginalUri(), getPictureCutUri(), PHOTO_REQUEST_CUT);
                    } else {
                        filesReceiving.receivePicture(getRealPathFromURI(getOriginalUri()));
                    }
                    return true;
                case PHOTO_REQUEST_CUT:  //裁剪
                    if (null == getPictureCutUri()) {
                        return true;
                    }
                    filesReceiving.receivePicture(getPictureCutUri().getPath().endsWith(".png") ? mPictureCutUri : getRealPathFromURI(mPictureCutUri));
                    return true;
                default:
                    return false;
            }
        } else {
            switch (requestCode) {
                case NULL:
                case MAKE_PHOTO:
                case PHOTO_ALBUM:
                case PHOTO_REQUEST_CUT:
                    filesReceiving.errorMessage("选择了取消");
                    return true;
                default:
                    return false;
            }
        }
    }

    /**
     * 创建空文件Uri
     */
    Uri newlyBuild() {
//        /storage/emulated/0/DCIM/temp/20170524120202.jpg
        return Uri.fromFile(createImageFile("IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()),
                ".png", getPictureDirectory()));
    }

    /**
     * 创建空缓存文件Uri
     */
    Uri newlyCacheBuild() {
        return Uri.fromFile(createImageFile("IMG_CACHE_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()),
                ".png", getPictureCacheDirectory()));
    }

    /**
     * 获取图片目录
     *
     * @return
     */
    File getPictureDirectory() {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/temp/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 获取切图缓存目录
     *
     * @return
     */
    File getPictureCacheDirectory() {
        File photoCacheDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/" + activity.getPackageName() + "/cache/img/");
//        File photoCacheDir = Glide.getPhotoCacheDir(activity);
        if (!photoCacheDir.exists()) {
            photoCacheDir.mkdirs();
        }
        return photoCacheDir;
    }

    /**
     * 给调用者的接口
     * 用于返回图片
     */
    public interface FilesReceiving {
        void receivePicture(Uri uri);

        void errorMessage(String error);
    }

    /**
     * 给调用者的接口
     * 调用者可以使用这个接口 对图片进行也锁裁剪等操作
     */
    public interface PictureCut {
        /**
         * @param intent      是这个可以调用系统裁剪功能
         * @param fromUri     裁剪目标Uri
         * @param toUri       裁剪到目标Uri
         * @param requestCode 官方指定Code
         */
        void shearCriterion(Intent intent, Uri fromUri, Uri toUri, int requestCode);
    }

    /**
     * 用户实现 确定剪切
     *
     * @param pictureCut
     */
    public void setPictureCut(PictureCut pictureCut) {
        mPictureCut = pictureCut;
    }

    /**
     * 有时系统返回的Uri 仅是图片编号  这时需要把它转为 Uri
     *
     * @param contentURI
     * @return
     */
    private Uri getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return Uri.parse(result);
    }

    /**
     * fileName 文件名称
     * fileType 文件类型
     * file 文件需要存于那个目录
     */
    File createImageFile(String fileName, String fileType, File file) {
        // Create an image file name
        try {
            return File.createTempFile(fileName, fileType, file);
        } catch (IOException e) {
//            filesReceiving.errorMessage("");
            return null;
        }
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 根据路径获得Bitmap并压缩返回bitmap用于显示
     *
     * @param uri       文件路径
     * @param reqWidth  宽度
     * @param reqHeight 高度
     * @return Bitmap  等比放小 不失真
     */
    public Bitmap getSmallBitmap(Uri uri, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;  //只返回图片的大小信息
        BitmapFactory.decodeFile(uri.getPath(), options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(uri.getPath(), options);
    }

//    /**
//     * 选择方式对话框
//     */
//    public ActionSheetDialog actionSheetDialogNoTitle() {
//        return new ActionSheetDialog(activity)
//                .builder()
//                .setCancelable(true)
//                .setCanceledOnTouchOutside(false)
//                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
//                        new ActionSheetDialog.OnSheetItemClickListener() {
//                            @Override
//                            public void onClick(int which) {
//                                // 这里添加意图启动手机自带照相机
//                                photoFromCamera();
//                            }
//                        })
//                .addSheetItem("从手机相册选择", ActionSheetDialog.SheetItemColor.Blue,
//                        new ActionSheetDialog.OnSheetItemClickListener() {
//                            @Override
//                            public void onClick(int which) {
//                                photoFromAlbum();
//                            }
//                        });
//    }

    /**
     * 切图加入缓存策略
     *
     * @param cacheName
     */
    public void addCutCache(String cacheName) {
        if (null == mPictureCutUri) return;
        String path = mPictureCutUri.getPath();
        String b = path.substring(0, path.lastIndexOf("/"));
        (new File(mPictureCutUri.getPath())).renameTo(new File(b + "/" + cacheName));
    }

    public void preservationBitmap(Bitmap bitmap) {

    }

    //获取拍照/相册的 Uri
    public Uri getOriginalUri() {
        return mOriginalUri;
    }

    //设置拍照/相册的 Uri
    public void setOriginalUri(Uri originalUri) {
        mOriginalUri = originalUri;
    }

    //获取裁剪的 Uri
    public Uri getPictureCutUri() {
        return mPictureCutUri;
    }

    //设置裁剪的 Uri
    public void setPictureCutUri(Uri pictureCutUri) {
        mPictureCutUri = pictureCutUri;
    }

}
