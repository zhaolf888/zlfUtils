package com.zlf.zlfutils;/**
 * Created by Administrator on 2017/6/12 0012.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.EditText;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：zhaolifeng
 * 时间：2017/06/12 17:42
 * 描述：Emoji  工具类    首先配置好表情的文字和图片id 以及顺序  然后初始化后 即可使用
 */
public class EmojiUtil {

    /**
     * 根据传入的字符串来匹配emoji表情
     * @param context 当前上下文
     * @param et 需要显示的输入框/文本框控件
     * @param source 需要验证的字符串
     * @return
     */
    public static void getEmotionContent(Context context, String source, EditText et){
        et.setText(getEmotionContent(context,source));
    }

    /**
     * 根据传入的字符串来匹配emoji表情
     * @param context 当前上下文
     * @param source 需要验证的字符串
     * @return
     */
    public static SpannableString getEmotionContent(Context context, String source){
        SpannableString spannableString = new SpannableString(source);
        String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]" ;
        Pattern patternEmotion = Pattern. compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);
        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes =  getImgByName(key);
            if (imgRes != -1) {
                // 压缩表情图片
//	                int size = (int) tv.getTextSize();
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imgRes);
                //压缩表情
//	                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
                ImageSpan span = new ImageSpan(context, bitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            }
        }
        return spannableString;
    }
    /**
     * 根据表情名称获取表情的资源id
     * @param key 传入的表情文字
     * @return 当前传入的字符串对应的表情资源id
     * @return -1匹配失败了
     */
    public static Integer getImgByName(String key){
        for (Emoji emoji : emojis) {
            if(key.equals(emoji.getText())){
                return emoji.getResourceId();
            }
        }
        return -1;
    }


    public class Emoji implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = -6193952810822846909L;
        public String id;//id
        public String text;//文字
        public int resourceId;//图片资源id
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getText() {
            return text;
        }
        public void setText(String text) {
            this.text = text;
        }
        public int getResourceId() {
            return resourceId;
        }
        public void setResourceId(int resourceId) {
            this.resourceId = resourceId;
        }
        public Emoji(String id, String text, int resourceId) {
            this.id = id;
            this.text = text;
            this.resourceId = resourceId;
        }
    }

    public static List<Emoji> emojis = new ArrayList<Emoji>();

    /**
     * emoji文字   这里的顺序和图片id的顺序保持一致
     */
    public static String[] emojiTexts = new String[]{"[龇牙]", "[调皮]", "[流汗]",
            "[偷笑]", "[再见]", "[敲打]", "[擦汗]", "[猪头]", "[玫瑰]", "[流泪]", "[大哭]",
            "[嘘]", "[酷]", "[抓狂]", "[委屈]", "[便便]", "[炸弹]", "[菜刀]", "[可爱]",
            "[色]", "[害羞]", "[得意]", "[吐]", "[微笑]", "[怒]", "[尴尬]", "[惊恐]",
            "[冷汗]", "[爱心]", "[示爱]", "[白眼]", "[傲慢]", "[难过]", "[惊讶]", "[疑问]",
            "[困]", "[么么哒]", "[憨笑]", "[爱情]", "[衰]", "[撇嘴]", "[阴险]", "[奋斗]",
            "[发呆]", "[右哼哼]", "[抱抱]", "[坏笑]", "[飞吻]", "[鄙视]", "[晕]", "[大兵]",
            "[可怜]", "[强]", "[弱]", "[握手]", "[胜利]", "[抱拳]", "[凋谢]", "[米饭]",
            "[蛋糕]", "[西瓜]", "[啤酒]", "[瓢虫]", "[勾引]", "[OK]", "[爱你]", "[咖啡]",
            "[月亮]", "[刀]", "[发抖]", "[差劲]", "[拳头]", "[心碎了]", "[太阳]", "[礼物]",
            "[皮球]", "[骷髅]", "[挥手]", "[闪电]", "[饥饿]", "[睡觉]", "[咒骂]", "[折磨]",
            "[抠鼻]", "[鼓掌]", "[糗大了]", "[左哼哼]", "[打哈欠]", "[快哭了]", "[吓]", "[篮球]",
            "[乒乓]", "[NO]", "[跳跳]", "[怄火]", "[转圈]", "[磕头]", "[回头]", "[跳绳]",
            "[激动]", "[街舞]", "[献吻]", "[左太极]", "[右太极]", "[闭嘴]", "[红双喜]", "[鞭炮]",
            "[红灯笼]", "[麻将]", "[麦克风]", "[礼品袋]", "[信封]", "[象棋]", "[彩带]", "[蜡烛]",
            "[爆筋]", "[棒棒糖]", "[奶瓶]", "[面条]", "[香蕉]", "[飞机]", "[汽车]", "[左车头]",
            "[车厢]", "[右车头]", "[多云]", "[下雨]", "[钞票]", "[熊猫]", "[灯泡]", "[风车]",
            "[闹钟]", "[雨伞]", "[彩球]", "[钻戒]", "[沙发]", "[纸巾]", "[药]", "[手枪]",
            "[青蛙]"};
/*

           此方法需要放在Application  初始化
    */
/**
     * 初始化emoji表情
     *//*

    public static  void initEmoji() {
        // TODO Auto-generated method stub
        for (int i = 1; i <= emojiTexts.length; i++) {  //这里的示例  图片的name 是f001
            Emoji emoji = new Emoji(0);
            if (i < 10) {
                emoji.setId("f00" + i);
            } else if (i < 100) {
                emoji.setId("f0" + i);
            } else {
                emoji.setId("f" + i);
            }
            emoji.setText(emojiTexts[i - 1]);
            try {
                Field field = R.drawable.class.getDeclaredField(emoji.getId());
                emoji.setResourceId(Integer.parseInt(field.get(null).toString()));
                emojis.add(emoji);
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
*/



}
