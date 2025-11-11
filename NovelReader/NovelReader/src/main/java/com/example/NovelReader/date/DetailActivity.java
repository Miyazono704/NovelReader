package com.example.NovelReader.date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.NovelReader.MyApplication;
import com.example.NovelReader.R;
import com.example.NovelReader.Tips;
import com.example.NovelReader.fileServe;
import com.example.NovelReader.javabean.Book;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detailImage)
    ImageView image;

    @BindView(R.id.detailName)
    TextView name;

    @BindView(R.id.detailPrice)
    TextView price;

    @BindView(R.id.detailContent)
    TextView detail;

    @BindView(R.id.detailAddCartBtn)
    Button addCart;

    @BindView(R.id.detailFavorite)
    ImageView favorite;

    String bookname;
    fileServe fileService;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        fileService=new fileServe(this);
        fullScreen(this);
        ButterKnife.bind(this);

        Book book = (Book) getIntent().getSerializableExtra("book");
        bookname= book.getName();
        if (book != null) {
            image.setImageResource(book.getImage());
            name.setText(book.getName());
            price.setText( book.getPrice()+" 万热度" );
            detail.setText(book.getDetail());

            addCart.setOnClickListener(v -> {
                if (!MyApplication.getCartBooks().contains(book)) {

                    MyApplication.getCartBooks().add(book);
                    Tips.show("已添加" + book.getName() + "到书架");

                    // 关闭Activity
                    finish();
                } else {
                    Tips.show("已在书架中，不能重复添加");
                }
            });
        }
    }

    @OnClick(R.id.detailBack)
    void clickBack() {
        finish();
    }
    @OnClick(R.id.detailName)
    void click(){
        if(bookname.equals("凡骨")){
            try {
                fileService.write("凡骨.text", "                      第1章 风雪夜，小太平叩门求炭 \n\n" +
                        "　　“砰、砰砰……”\n" +
                        "\n" +
                        "　　呼啸的寒风中，一个衣着单薄的小男孩，哆哆嗦嗦地敲响了一户人家的院门。\n" +
                        "\n" +
                        "　　此时正值除夕，院内不时响起欢笑声。\n" +
                        "\n" +
                        "　　“砰、砰砰……”\n" +
                        "\n" +
                        "　　等了许久不见回应，小男孩又抬手拉住门环，轻轻扣了几下。\n" +
                        "\n" +
                        "　　“呼……”\n" +
                        "\n" +
                        "　　天太冷了，扣门之后，他立刻将手放在嘴边哈了口气，暖和一下。\n" +
                        "\n" +
                        "　　不止是手，他那双只穿着草鞋的脚，也已经冻得快没知觉了。\n" +
                        "\n" +
                        "　　“谁啊？”\n" +
                        "\n" +
                        "　　好在这时，院子里的人终于有了回应。\n" +
                        "\n" +
                        "　　小男孩面上一喜。\n" +
                        "\n" +
                        "　　“二叔，是我。”\n" +
                        "\n" +
                        "　　他赶紧应了一声。\n" +
                        "\n" +
                        "　　“吱呀……”\n" +
                        "\n" +
                        "　　没过多久，院门被打开了，一名相貌敦厚的中年人，从门缝之中探出一个脑袋来。\n" +
                        "\n" +
                        "　　“哦，是太平啊，这么晚了，有什么事吗？”\n" +
                        "\n" +
                        "　　中年人向小男孩问道。\n" +
                        "\n" +
                        "　　“ 二 ……二叔……家里的炭烧完了，我想，我想跟二叔你借几斤炭，来年开春，能上山砍柴了，我一定还你。”\n" +
                        "\n" +
                        "　　小男孩有些不好意思地目光躲闪道。\n" +
                        "\n" +
                        "　　“几斤炭能值几个钱？你且在这里等着，叔这就给你拿去！”\n" +
                        "\n" +
                        "　　男子摆了摆手，一口答应下来。\n" +
                        "\n" +
                        "　　“太平，你吃饭了没？”\n"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, DetailBook.class);
            startActivity(intent);
        }else if(bookname.equals("第九特区")){
            try {
                fileService.write("凡骨.text","　                      第一章 初来乍到\n\n" +
                        "   亚洲北方，第九特区，松江市。\n" +
                        "\n" +
                        "　　秦禹站在隶属于市警务厅，黑街区警务司的办公楼内，笑着冲一位中年问道：“可以了吗？”\n" +
                        "\n" +
                        "　　“嗯，进来吧。”中年摆了摆手，转身就走进了左侧的办公室。\n" +
                        "\n" +
                        "　　秦禹闻声整理了一下衣衫，迈步跟着中年就走了进去。\n" +
                        "\n" +
                        "　　办公室不算小，约有六十几平米，但屋内办公桌后，却只坐着一人。看样能有四十岁左右，留着八字胡，脸上横肉明显，模样很凶。\n" +
                        "\n" +
                        "　　中年走到办公桌前，将两沓子资料放在了八字胡男子面前，轻声说道：“司长，这是最后一个了。”\n" +
                        "\n" +
                        "　　“体检了？”八字胡拿起资料问。\n" +
                        "\n" +
                        "　　“是的。”\n" +
                        "\n" +
                        "　　“嗯，你出去吧。”\n" +
                        "\n" +
                        "　　“好。”\n" +
                        "\n" +
                        "　　二人简短交流了几句后，中年离去，而秦禹则是往前走了两步，站在办公桌前面没再说话，只看着八字胡检查自己的资料。\n" +
                        "\n" +
                        "　　办公桌内，八字胡皱眉盯着资料，轻声念了起来：“秦禹，22岁，七十五公斤，一米八二……纪元年前生人，祖籍贯H省J市。呵呵，这离现在的松江也不远啊。来入职前是在待规划区生活，父母失踪（疑死亡），无亲属……嗯？你这履历怎么是空白的啊？”\n" +
                        "\n" +
                        "　　“我就没履历啊。”秦禹笑着应道：“在待规划区活着都费劲，什么有饭吃就干什么，哪有履历啊？”\n" +
                        "\n" +
                        "　　“呵呵。”八字胡一笑：“你来之前倒是随便写两个啊，这没履历录系统也不好看啊。”\n" +
                        "\n" +
                        "　　“行，回头我填一些。”秦禹也没争辩，立马附和了一句。\n" +
                        "\n" +
                        "　　八字胡盯着资料又问：“没履历，也就是说没有服役过，那你有过使用枪械的经验吗？”\n" +
                        "\n" +
                        "　　秦禹毫不犹豫的摇头：“没有。”\n" +
                        "\n" +
                        "　　“有过刑事处罚的劣迹吗？”\n" +
                        "\n" +
                        "　　“没有。”\n" +
                        "\n" +
                        "　　八字胡沉吟半晌，慢慢放下手头资料，抬头看着秦禹笑呵呵的说道：“在待规划区那个没法律，没约束的地方，能混到掏钱买第九特区工作和居住许可的地步。你这小子……有点经历啊。”\n" +
                        "\n" +
                        "　　“哪有，”秦禹龇牙一笑：“只是运气比较好，遇到点贵人。”\n" +
                        "\n" +
                        "　　八字胡端起水杯，抬头打量着秦禹，象征性的点了点头：“嗯，小伙看着挺精神的。”\n" +
                        "\n" +
                        "　　秦禹抿嘴一笑，没有接话。\n" +
                        "\n" +
                        "　　八字胡放下茶杯，插手看着秦禹，话语简洁的叮嘱道：“第九特区的情况比较特殊，它虽然隶属于联合政府的行政序列，但拥有高度自由的自治权利，跟八大区有着本质区别。这里多民族混合，除了咱们黄种人，黑人白人也不少……社会环境非常复杂，部分地区也确实存在咱们想改变，又暂时改变不了的混乱现象。身为警务人员，你要从全方位适应这里的环境。”juzixs.ČŐM\n" +
                        "\n" +
                        "　　“明白。”秦禹表情严肃的点头。\n" +
                        "\n" +
                        "　　“还有，我不管你有过什么经历，但在我身边吃饭，是龙你得盘着，是虎你得卧着。添麻烦，找事儿，我马上收拾你。”八字胡插着双手，话语平淡的提醒着。\n" +
                        "\n" +
                        "　　“李司，我来是帮你减少麻烦的。”秦禹嘿嘿一笑。\n" +
                        "\n" +
                        "　　“呵呵。”八字胡一笑，在办公桌触屏电话上点了几下，低头将嘴对准了收音麦克。\n" +
                        "\n" +
                        "　　数秒后，一个男性声音响起：“您好司长，这里是第一刑侦队。”\n" +
                        "\n" +
                        "　　“袁克呢？”李司长问。\n" +
                        "\n" +
                        "　　“……袁队不在，刚出去。”\n" +
                        "\n" +
                        "　　“给你们添个新人，赶紧过来领走。”\n" +
                        "\n" +
                        "　　“好的，好的。”\n" +
                        "\n" +
                        "　　“就这样。”李司长摸了摸八字胡，伸手按了挂断键：“去门口等着吧，一会有人来领你，具体的规矩到了队里学吧。”\n" +
                        "\n" +
                        "　　“好的，李司。”秦禹点头后，立马往前走了两步，低头从兜里掏出一个很小的黑袋子放在了桌面上：“小祁特意嘱咐过，说特区警务系统现在是最不好进的工作，没有您帮忙，我排职都不知道得排多久，所以千万别忘了礼节。”\n" +
                        "\n" +
                        "　　李司长顺手拿起袋子打开一看，见到里面放着一颗约有黄豆粒儿大小的钻石时，略微一怔：“你们待规划区有能人啊，这东西都能搞到？！哎呦，这都多少年没看到了。”\n" +
                        "\n" +
                        "　　秦禹笑了笑，没接话。\n" +
                        "\n" +
                        "　　李司长将小袋子顺手扔进抽屉里锁上，抬头指着秦禹又说了一句：“岁数不大，你看着还真有点质感。”\n" +
                        "\n" +
                        "　　“我也就这点家底儿。”秦禹假装憨厚的挠了挠头，见李司长没有马上离开吃午饭，就跟他多聊了几句。\n" +
                        "\n" +
                        "　　几分钟后，一个体态较胖，跟秦禹同龄的青年迈步进屋，腰杆挺的笔直，敬礼喊道：“报告李司，一队三级警员齐麟奉命领新同事回队。”\n" +
                        "\n" +
                        "　　李司长闻声拍了拍秦禹的胳膊：“好好干，争取年底评选能让我看到你哈。”\n" +
                        "\n" +
                        "　　“哎。”秦禹点头。\n" +
                        "\n" +
                        "　　“行了，去队里吧。”李司长顺手指着齐麟说道：“告诉袁克照顾照顾这小伙。”\n" +
                        "\n" +
                        "　　一颗钻，秦禹多跟李司长聊了不到十分钟，多得到了一句照顾照顾，但似乎也就仅此而已。\n" +
                        "\n" +
                        "　　……\n" +
                        "\n" +
                        "　　走廊内。\n" +
                        "\n" +
                        "　　胖乎乎的齐麟走在秦禹左侧，很健谈的问道：“哪儿来的啊，兄弟？”\n" +
                        "\n" +
                        "　　“待规划区。”\n" +
                        "\n" +
                        "　　“从那个鬼地方来的？”齐麟一怔：“那挺不容易啊。”\n" +
                        "\n" +
                        "　　“有点小运气。”秦禹一笑。\n" +
                        "\n" +
                        "　　齐麟点了点头，也就没有再继续刨根问底。因为这个年头，吃喝缺，活着难，谁身上可能都有点不为人知的秘密。");
            }catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this,DetailBook.class);
            startActivity(intent);
        }
        else{
            Tips.show("暂未上架资源！");
        }
    }

    @OnClick(R.id.detailFavorite)
    void clickFavorite() {
        favorite.setImageResource(R.drawable.ic_baseline_favorite_24dp);
        Tips.show("打赏成功！！！");
    }

    /**
     * 通过设置全屏，设置状态栏透明
     */
    private void fullScreen(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = ((Window) window).getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            WindowManager.LayoutParams attributes = window.getAttributes();
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            attributes.flags |= flagTranslucentStatus;
            window.setAttributes(attributes);
        }
    }
}