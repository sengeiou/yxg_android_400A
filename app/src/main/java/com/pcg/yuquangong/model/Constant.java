package com.pcg.yuquangong.model;

import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;

public class Constant {

    public static class Event{
        public static final int EVENT_ON_LOCATION_CHANGE = 123;
    }

    public static class Banner {
        public static final String IMAGE_BANNER = "image";
        public static final String ARTICEL_BANNER = "article";
        public static final String VIDEO_BANNER = "video";
    }

    public static final int FORCE_APP_UPDATE = 1;

    public static final int DEFAULT_MEMBER_ID = 5; // 游客上传的member_id，接口所有操作都是以member_id做唯一标识
    public static final int DEFAULT_CALENDAR = Member.CALENDAR; // 治疗详情显示用的calendar

    public static final int JUST_SHOW_WATCH_RECORD_DETAIL = -999;

    public static class Alarm {
        public static final int ALARM_OK_STATE = 0;
        public static final int ALARM_STATE = 1;
    }

    public static class Cache {
        public static final String KEY_AD_CODE = "key_ad_code";
        public static final String KEY_DEVICE_ID = "key_device_id";
        public static final String KEY_LOGINED_DATA = "key_logined_data";
        public static final String KEY_SUPER_LOGINED_DEVICE_NO = "key_super_logined_deviceno";
        public static final String KEY_HOME_BANNER_DATA = "key_home_banner_data";
        public static final String KEY_SETTING_ABOUT_DATA = "key_setting_about_data";
        public static final String KEY_SETTING_LANGUAGE_TYPE = "key_setting_language_type";
        public static final String KEY_SETTING_DEVICES_DATA = "key_setting_devices_data";
        public static final String KEY_SETTING_PROFILE_DATA = "key_setting_profile_data";
        public static final String KEY_IS_AUDIO_TIPS_OPEN = "key_is_audio_tips_open";

        public static final String KEY_MEMBER_LIST_DATA = "key_member_list_data";
        public static final String KEY_CURE_RECORD_LIST_DATA = "key_cure_record_list_data";
        public static final String KEY_WATCH_RECORD_LIST_DATA = "key_watch_record_list_data";
        public static final String KEY_WATCH_HISTOR_LIST_DATA = "key_watch_history_list_data";

        public static final String KEY_PHONE_NATIONAL_CODE_DATA = "key_phone_zcode";
    }

    public static class Language {
        public static final int LAN_CN = 1;
        public static final int LAN_TW = 2;
        public static final int LAN_EN = 3;
    }

    public static class Sorting {
        public static final int PAIN = 1; // 疼痛
        public static final int INFLAMMATION = 2; // 炎症
        public static final int THE_LUMP = 3; // 肿块
        public static final int THREE_HIGH = 4; // 三高症
        public static final int THREE_HIGH_GXY = 5; // 三高症-高血压
        public static final int THREE_HIGH_TNB = 6; // 三高症-糖尿病
        public static final int THREE_HIGH_GXZ = 7; // 三高症-高血脂

        public static final int SORTING_METHOD_TT = 1; // 疼痛类
        public static final int SORTING_METHOD_YZ = 2; // 炎症类
        public static final int SORTING_METHOD_ZK = 3; // 肿块类
        public static final int SORTING_METHOD_GXY = 4; // 高血压
        public static final int SORTING_METHOD_TNB = 5; // 糖尿病
        public static final int SORTING_METHOD_GXZ = 6; // 高血脂

        public static final int LITTLE = 1; // 低
        public static final int MIDDLE = 2; // 中
        public static final int HIGN = 3; // 高

        public static final int CURE_LEFT_DEVICE = 0;
        public static final int CURE_RIGHT_DEVICE = 1;
        public static final int CURE_DOUBLE_DEVICE = 2;

    }

    public static class ChooseSorting {
        public static final String KEY_DISEASE_TYPE = "key_disease_type"; // 病类型
        public static final String KEY_BIRTHDAY = "key_birthday";
        public static final String KEY_CALENDAR = "key_calendar";
        public static final String KEY_MEMBER_ID = "key_member_id";
        public static final String KEY_NAME = "key_name";
        public static final String KEY_SYMPTOM = "key_symptom"; // 具体是哪种病情，1-6，上传服务器
        public static final String KEY_COMB_MPA = "key_comb_mpa"; // 病情的轻度、中度、重度
        public static final String KEY_COMB_HEAD = "key_comb_head"; // 左、双、右梳理头
    }

    public static class Member {
        public static final String KEY_BIRTHDAY_YEAR = "key_year";
        public static final String KEY_BIRTHDAY_MONTH = "key_month";
        public static final String KEY_BIRTHDAY_DAY = "key_day";
        public static final String KEY_BIRTHDAY_CALENDAR_TYPE = "key_calendar_type";

        public static final String KEY_MEASURE_MEMBER = "key_measure_member";

        public static final int CALENDAR = 1; //公历
        public static final int LUNAR_CALENDAR = 2; //农历

        public static final int GENDER_BOY = 1; // 男孩
        public static final int GENDER_GIRL = 2; // 女孩
    }

    // 59 58 03 05 02 1C 00 00
    public static final byte[] Serial_Port_START_CURE = {0x59, 0x58, 0x03, 0x05,
            0x02, 0x1C, 0x00, 0x00};

    /**
     * 穴位坐标
     * 坐标原点左上角，横向为x轴（在前），纵向为y轴（后）
     * 单位：px
     * 蠡沟：290 236    中都：350 242   膝关：542 251
     * 曲泉：582 264    中冲：27  245   劳宫：307 408
     * 大陵: 333 225    内关：404 232   间使：444 238
     * 郗门；519 242    曲泽：683 246   至阴：38  270
     * 束骨：81  260    金门：137 247   仆参：170 208
     * 昆仑：224 225    跗阳：304 240   飞扬：372 237
     * 承山：394 216    承筋：473 216   合阳：536 216
     * 委中：634 211    浮郄：693 247   少泽：95  174
     * 前谷：136 171    后溪：175 167   腕谷：235 172
     * 阳谷：274 190    养老：307 197   支正：401 187
     * 小海：616 187    关冲：49  257   夜门：154 244
     * 中渚：210 280    阳池：280 256   外关：335 264
     * 支沟：378 266    会宗：368 276   三阳络：420 272
     * 四渎：505 280    天井：636 280   清冷渊：702 280
     * 梁丘：75  282    犊鼻：131 279   足三里：224 284
     * 上巨虚：296 284  条口：363 276   下巨虚：414 267
     * 丰隆：389 293    解溪：574 203   冲阳：622 175
     * 陷谷：643 157    内廷：673 151   厉兑：694 100
     * 手五里：68 241   肘髎：138 247   曲池：170 229
     * 手三里：240 228   上廉：291 225   下廉：337 224
     * 温溜：401 225     偏历：453 222   阳关：540 212
     * 合谷：585 191     三间：635 196   二间：684 203
     * 商阳：752 211     足窍阴：79 100  侠溪：95  130
     * 足临泣：107 158   地五会：120 179 丘墟：161 206
     * 悬钟：253 251     阳辅：291 252   光明：325 253
     * 外丘：393 246     阳交：403 287   阳陵泉：556 233
     * 膝阳关：622 254   少府：190 266   神门：290 254
     * 阴郗：310 255     通里：331 257   灵道：358 259
     * 少海：644 295     青灵：726 292   然谷：74  192
     * 照海：104 245     水泉：94  270   太溪：141 271
     * 复溜：207 287     交信：216 261   筑宾：313 283
     * 阴谷：294 262     大敦：151 281   行间：210 272
     * 太冲：301 263     中封：472 266   少商：183 167
     * 鱼际：249 198     太渊：308 229   经渠：345 239
     * 列缺：349 234     孔最：511 246   尺泽：617 259
     */
    public static class AcupointCoordinate {

        //列缺：349 234     孔最：511 246   尺泽：617 259
        public static final int[] CHI_ZE = {617, 259};
        public static final int[] KONG_ZUI = {511, 246};
        public static final int[] LIE_QUE = {349, 234};

        //鱼际：249 198     太渊：308 229   经渠：345 239
        public static final int[] JING_QU = {345, 239};
        public static final int[] TAI_YUAN = {308, 229};
        public static final int[] YU_JI = {249, 198};

        //太冲：301 263     中封：472 266   少商：183 167
        public static final int[] SHAO_SHANG = {183, 167};
        public static final int[] ZHONG_FENG = {472, 266};
        public static final int[] TAI_CHONG = {301, 263};

        //阴谷：294 262     大敦：151 281   行间：210 272
        public static final int[] XING_JIAN = {210, 272};
        public static final int[] DA_DUN = {151, 281};
        public static final int[] YIN_GU = {294, 262};

        //复溜：207 287     交信：216 261   筑宾：313 283
        public static final int[] ZHU_BIN = {313, 283};
        public static final int[] JIAO_XIN = {216, 261};
        public static final int[] FU_LIU = {207, 287};

        // * 照海：104 245     水泉：94  270   太溪：141 271
        public static final int[] TAI_XI = {141, 271};
        public static final int[] SHUI_QUAN = {94, 270};
        public static final int[] ZHAO_HAI = {104, 245};

        // 少海：644 295     青灵：726 292   然谷：74  192
        public static final int[] RAN_GU = {74, 192};
        public static final int[] QING_LIN = {726, 292};
        public static final int[] SHAO_HAI = {644, 295};

        // 阴郗：310 255     通里：331 257   灵道：358 259
        public static final int[] LING_DAO = {358, 259};
        public static final int[] TONG_LI = {331, 257};
        public static final int[] YIN_XI = {310, 255};

        // 膝阳关：622 254   少府：190 266   神门：290 254
        public static final int[] SHEN_MEN = {290, 254};
        public static final int[] SHAO_FU = {190, 266};
        public static final int[] XI_YANG_GUAN = {622, 254};

        // 外丘：393 246     阳交：403 287   阳陵泉：556 233
        public static final int[] YANG_LING_QUAN = {556, 233};
        public static final int[] YANG_JIAO = {403, 287};
        public static final int[] WAI_QIU = {393, 246};

        // 悬钟：253 251     阳辅：291 252   光明：325 253
        public static final int[] GUANG_MING = {325, 253};
        public static final int[] YANG_FU = {291, 252};
        public static final int[] XUAN_ZHONG = {253, 251};

        //足临泣：107 158   地五会：120 179 丘墟：161 206
        public static final int[] QIU_XU = {161, 206};
        public static final int[] DI_WU_HUI = {120, 179};
        public static final int[] ZU_LIN_QI = {107, 158};

        //商阳：752 211     足窍阴：79 100  侠溪：95  130
        public static final int[] XIA_XI = {95, 130};
        public static final int[] ZU_QIAO_YIN = {79, 100};
        public static final int[] SHANG_YANG = {752, 211};

        // 合谷：585 191     三间：635 196   二间：684 203
        public static final int[] ER_JIAN = {684, 203};
        public static final int[] SAN_JIAN = {635, 196};
        public static final int[] HE_GU = {585, 191};

        // 温溜：401 225    偏历：453 222   阳关：540 212
        public static final int[] YANG_GUAN = {540, 212};
        public static final int[] BIAN_LI = {453, 222};
        public static final int[] WEN_LIU = {401, 225};

        //手三里：240 228   上廉：291 225   下廉：337 224
        public static final int[] XIA_LIAN = {337, 224};
        public static final int[] SHANG_LIAN = {291, 225};
        public static final int[] SHOU_SAN_LI = {240, 228};

        // 手五里：68 241   肘髎：138 247   曲池：170 229
        public static final int[] QU_CHI = {170, 229};
        public static final int[] ZHOU_LIAO = {138, 247};
        public static final int[] SHOU_WU_LI = {68, 241};

        //陷谷：643 157    内廷：673 151   厉兑：694 100
        public static final int[] LI_DUI = {694, 100};
        public static final int[] NEI_TING = {673, 151};
        public static final int[] XIAN_GU = {643, 157};

        //丰隆：389 293    解溪：574 203   冲阳：622 175
        public static final int[] CHONG_YANG = {622, 175};
        public static final int[] JIE_XI = {574, 203};
        public static final int[] FENG_LONG = {389, 293};

        //上巨虚：296 284  条口：363 276   下巨虚：414 267
        public static final int[] XIA_JU_XU = {414, 267};
        public static final int[] TIAO_KOU = {363, 276};
        public static final int[] SHANG_JU_XU = {296, 284};

        //梁丘：75  282    犊鼻：131 279   足三里：224 284
        public static final int[] ZU_SAN_LI = {224, 284};
        public static final int[] DU_BI = {131, 279};
        public static final int[] LIANG_QIU = {75, 282};

        // 四渎：505 280    天井：636 280   清冷渊：702 280
        public static final int[] LENG_QING_YUAN = {702, 280};
        public static final int[] TIAN_JING = {636, 280};
        public static final int[] SI_DU = {505, 280};

        //支沟：378 266    会宗：368 276   三阳络：420 272
        public static final int[] SAN_YANG_LUO = {420, 272};
        public static final int[] HUI_ZONG = {378, 283};
        public static final int[] ZHI_GOU = {378, 266};

        // 中渚：210 280    阳池：280 256   外关：335 264
        public static final int[] WAI_GUAN = {335, 264};
        public static final int[] YANG_CHI = {280, 256};
        public static final int[] ZHONG_ZHU = {210, 280};

        //小海：616 187    关冲：49  257   夜门：154 244
        public static final int[] YE_MEN = {154, 244};
        public static final int[] GUAN_CHONG = {49, 257};
        public static final int[] XIAO_HAI = {616, 187};

        // 阳谷：274 190    养老：307 197   支正：401 187
        public static final int[] ZHI_ZHENG = {401, 187};
        public static final int[] YANG_LAO = {307, 197};
        public static final int[] YANG_GU = {274, 190};

        //前谷：136 171    后溪：175 167   腕谷：235 172
        public static final int[] WAN_GU = {235, 172};
        public static final int[] HOU_XI = {175, 167};
        public static final int[] QIAN_GU = {136, 171};

        // 委中：634 211    浮郄：693 247   少泽：95  174
        public static final int[] SHAO_ZE = {95, 174};
        public static final int[] FU_XI = {693, 247};
        public static final int[] WEI_ZHONG = {634, 211};

        //承山：394 216    承筋：473 216   合阳：536 216
        public static final int[] HE_YANG = {536, 216};
        public static final int[] CHEN_JING = {473, 216};
        public static final int[] CHEN_SHAN = {394, 216};

        //昆仑：224 225    跗阳：304 240   飞扬：372 237
        public static final int[] FEI_YANG = {372, 237};
        public static final int[] FU_YANG = {304, 240};
        public static final int[] KUN_LUN = {224, 225};

        // 束骨：81  260    金门：137 247   仆参：170 208
        public static final int[] PU_CAN = {170, 208};
        public static final int[] JIN_MEN = {137, 247};
        public static final int[] SHU_GU = {81, 260};

        //郗门；519 242    曲泽：683 246   至阴：38  270
        public static final int[] ZHI_YIN = {38, 270};
        public static final int[] QU_ZE = {683, 246};
        public static final int[] XI_MEN = {519, 242};

        //大陵: 333 225    内关：404 232   间使：444 238
        public static final int[] JIAN_SHI = {444, 238};
        public static final int[] NEI_GUAN = {404, 232};
        public static final int[] DA_LIN = {333, 225};

        //曲泉：582 264    中冲：27  245   劳宫：307 408
        public static final int[] LAO_GONG = {194, 215};
        public static final int[] ZHONG_CHONG = {27, 245};
        public static final int[] QU_QUAN = {582, 264};

        //蠡沟：290 236    中都：350 242   膝关：542 251
        public static final int[] XI_GUAN = {542, 251};
        public static final int[] ZHONG_DU = {350, 242};
        public static final int[] LI_GOU = {290, 236};

    }

    public static class AcupointNumber {
        //列缺：349 234     孔最：511 246   尺泽：617 259
        public static final int CHI_ZE = 105;
        public static final int KONG_ZUI = 104;
        public static final int LIE_QUE = 103;

        //鱼际：249 198     太渊：308 229   经渠：345 239
        public static final int JING_QU = 102;
        public static final int TAI_YUAN = 101;
        public static final int YU_JI = 100;

        //太冲：301 263     中封：472 266   少商：183 167
        public static final int SHAO_SHANG = 99;
        public static final int ZHONG_FENG = 98;
        public static final int TAI_CHONG = 97;

        //阴谷：294 262     大敦：151 281   行间：210 272
        public static final int XING_JIAN = 96;
        public static final int DA_DUN = 95;
        public static final int YIN_GU = 94;

        //复溜：207 287     交信：216 261   筑宾：313 283
        public static final int ZHU_BIN = 93;
        public static final int JIAO_XIN = 92;
        public static final int FU_LIU = 91;

        // * 照海：104 245     水泉：94  270   太溪：141 271
        public static final int TAI_XI = 90;
        public static final int SHUI_QUAN = 89;
        public static final int ZHAO_HAI = 88;

        // 少海：644 295     青灵：726 292   然谷：74  192
        public static final int RAN_GU = 87;
        public static final int QING_LIN = 86;
        public static final int SHAO_HAI = 85;

        // 阴郗：310 255     通里：331 257   灵道：358 259
        public static final int LING_DAO = 84;
        public static final int TONG_LI = 83;
        public static final int YIN_XI = 82;

        // 膝阳关：622 254   少府：190 266   神门：290 254
        public static final int SHEN_MEN = 81;
        public static final int SHAO_FU = 80;
        public static final int XI_YANG_GUAN = 79;

        // 外丘：393 246     阳交：403 287   阳陵泉：556 233
        public static final int YANG_LING_QUAN = 78;
        public static final int YANG_JIAO = 77;
        public static final int WAI_QIU = 76;

        // 悬钟：253 251     阳辅：291 252   光明：325 253
        public static final int GUANG_MING = 75;
        public static final int YANG_FU = 74;
        public static final int XUAN_ZHONG = 73;

        //足临泣：107 158   地五会：120 179 丘墟：161 206
        public static final int QIU_XU = 72;
        public static final int DI_WU_HUI = 71;
        public static final int ZU_LIN_QI = 70;

        //商阳：752 211     足窍阴：79 100  侠溪：95  130
        public static final int XIA_XI = 69;
        public static final int ZU_QIAO_YIN = 68;
        public static final int SHANG_YANG = 67;

        // 合谷：585 191     三间：635 196   二间：684 203
        public static final int ER_JIAN = 66;
        public static final int SAN_JIAN = 65;
        public static final int HE_GU = 64;

        // 温溜：401 225    偏历：453 222   阳关：540 212
        public static final int YANG_GUAN = 63;
        public static final int BIAN_LI = 62;
        public static final int WEN_LIU = 61;

        //手三里：240 228   上廉：291 225   下廉：337 224
        public static final int XIA_LIAN = 60;
        public static final int SHANG_LIAN = 59;
        public static final int SHOU_SAN_LI = 58;

        // 手五里：68 241   肘髎：138 247   曲池：170 229
        public static final int QU_CHI = 57;
        public static final int ZHOU_LIAO = 56;
        public static final int SHOU_WU_LI = 55;

        //陷谷：643 157    内廷：673 151   厉兑：694 100
        public static final int LI_DUI = 54;
        public static final int NEI_TING = 53;
        public static final int XIAN_GU = 52;

        //丰隆：389 293    解溪：574 203   冲阳：622 175
        public static final int CHONG_YANG = 51;
        public static final int JIE_XI = 50;
        public static final int FENG_LONG = 49;

        //上巨虚：296 284  条口：363 276   下巨虚：414 267
        public static final int XIA_JU_XU = 48;
        public static final int TIAO_KOU = 47;
        public static final int SHANG_JU_XU = 46;

        //梁丘：75  282    犊鼻：131 279   足三里：224 284
        public static final int ZU_SAN_LI = 45;
        public static final int DU_BI = 44;
        public static final int LIANG_QIU = 43;

        // 四渎：505 280    天井：636 280   清冷渊：702 280
        public static final int LENG_QING_YUAN = 42;
        public static final int TIAN_JING = 41;
        public static final int SI_DU = 40;

        //支沟：378 266    会宗：368 276   三阳络：420 272
        public static final int SAN_YANG_LUO = 39;
        public static final int HUI_ZONG = 38;
        public static final int ZHI_GOU = 37;

        // 中渚：210 280    阳池：280 256   外关：335 264
        public static final int WAI_GUAN = 36;
        public static final int YANG_CHI = 35;
        public static final int ZHONG_ZHU = 34;

        //小海：616 187    关冲：49  257   夜门：154 244
        public static final int YE_MEN = 33;
        public static final int GUAN_CHONG = 32;
        public static final int XIAO_HAI = 31;

        // 阳谷：274 190    养老：307 197   支正：401 187
        public static final int ZHI_ZHENG = 30;
        public static final int YANG_LAO = 29;
        public static final int YANG_GU = 28;

        //前谷：136 171    后溪：175 167   腕谷：235 172
        public static final int WAN_GU = 27;
        public static final int HOU_XI = 26;
        public static final int QIAN_GU = 25;

        // 委中：634 211    浮郄：693 247   少泽：95  174
        public static final int SHAO_ZE = 24;
        public static final int FU_XI = 23;
        public static final int WEI_ZHONG = 22;

        //承山：394 216    承筋：473 216   合阳：536 216
        public static final int HE_YANG = 21;
        public static final int CHEN_JING = 20;
        public static final int CHEN_SHAN = 19;

        //昆仑：224 225    跗阳：304 240   飞扬：372 237
        public static final int FEI_YANG = 18;
        public static final int FU_YANG = 17;
        public static final int KUN_LUN = 16;

        // 束骨：81  260    金门：137 247   仆参：170 208
        public static final int PU_CAN = 15;
        public static final int JIN_MEN = 14;
        public static final int SHU_GU = 13;

        //郗门；519 242    曲泽：683 246   至阴：38  270
        public static final int ZHI_YIN = 12;
        public static final int QU_ZE = 11;
        public static final int XI_MEN = 10;

        //大陵: 333 225    内关：404 232   间使：444 238
        public static final int JIAN_SHI = 9;
        public static final int NEI_GUAN = 8;
        public static final int DA_LIN = 7;

        //曲泉：582 264    中冲：27  245   劳宫：307 408
        public static final int LAO_GONG = 6;
        public static final int ZHONG_CHONG = 5;
        public static final int QU_QUAN = 4;

        //蠡沟：290 236    中都：350 242   膝关：542 251
        public static final int XI_GUAN = 3;
        public static final int ZHONG_DU = 2;
        public static final int LI_GOU = 1;

        // 没有找到返回一个错误值
        public static final int ERROR_POINT = -111;
    }

    public static class AcupointUtils {

        public static String getAcupointStringByNumber(int acupointNum) {
            switch (acupointNum) {
                case AcupointNumber.SHAO_SHANG:
                    return "少商";
                case AcupointNumber.YU_JI:
                    return "鱼际";
                case AcupointNumber.TAI_YUAN:
                    return "太渊";
                case AcupointNumber.JING_QU:
                    return "经渠";
                case AcupointNumber.LIE_QUE:
                    return "列缺";
                case AcupointNumber.KONG_ZUI:
                    return "孔最";
                case AcupointNumber.CHI_ZE:
                    return "尺泽";

                case AcupointNumber.DA_DUN:
                    return "大敦";
                case AcupointNumber.XING_JIAN:
                    return "行间";
                case AcupointNumber.TAI_CHONG:
                    return "太冲";
                case AcupointNumber.ZHONG_FENG:
                    return "中封";

                case AcupointNumber.YIN_GU:
                    return "阴谷";
                case AcupointNumber.ZHU_BIN:
                    return "筑宾";
                case AcupointNumber.JIAO_XIN:
                    return "交信";
                case AcupointNumber.FU_LIU:
                    return "复溜";
                case AcupointNumber.TAI_XI:
                    return "太溪";
                case AcupointNumber.SHUI_QUAN:
                    return "水泉";
                case AcupointNumber.ZHAO_HAI:
                    return "照海";
                case AcupointNumber.RAN_GU:
                    return "然谷";

                case AcupointNumber.QING_LIN:
                    return "青灵";
                case AcupointNumber.SHAO_HAI:
                    return "少海";
                case AcupointNumber.LING_DAO:
                    return "灵道";
                case AcupointNumber.TONG_LI:
                    return "通里";
                case AcupointNumber.YIN_XI:
                    return "阴郗";
                case AcupointNumber.SHEN_MEN:
                    return "神门";
                case AcupointNumber.SHAO_FU:
                    return "少府";

                case AcupointNumber.XI_YANG_GUAN:
                    return "膝阳关";
                case AcupointNumber.YANG_LING_QUAN:
                    return "阳陵泉";
                case AcupointNumber.YANG_JIAO:
                    return "阳交";
                case AcupointNumber.WAI_QIU:
                    return "外丘";
                case AcupointNumber.GUANG_MING:
                    return "光明";
                case AcupointNumber.YANG_FU:
                    return "阳辅";
                case AcupointNumber.XUAN_ZHONG:
                    return "悬钟";
                case AcupointNumber.QIU_XU:
                    return "丘墟";
                case AcupointNumber.DI_WU_HUI:
                    return "地五会";
                case AcupointNumber.ZU_LIN_QI:
                    return "足临泣";
                case AcupointNumber.XIA_XI:
                    return "侠溪";
                case AcupointNumber.ZU_QIAO_YIN:
                    return "足窍阴";

                case AcupointNumber.SHANG_YANG:
                    return "商阳";
                case AcupointNumber.ER_JIAN:
                    return "二间";
                case AcupointNumber.SAN_JIAN:
                    return "三间";
                case AcupointNumber.HE_GU:
                    return "合谷";
                case AcupointNumber.YANG_GUAN:
                    return "阳关";
                case AcupointNumber.BIAN_LI:
                    return "偏历";
                case AcupointNumber.WEN_LIU:
                    return "温溜";
                case AcupointNumber.XIA_LIAN:
                    return "下廉";
                case AcupointNumber.SHANG_LIAN:
                    return "上廉";
                case AcupointNumber.SHOU_SAN_LI:
                    return "手三里";
                case AcupointNumber.QU_CHI:
                    return "曲池";
                case AcupointNumber.ZHOU_LIAO:
                    return "肘髎";
                case AcupointNumber.SHOU_WU_LI:
                    return "手五里";

                case AcupointNumber.LI_DUI:
                    return "厉兑";
                case AcupointNumber.NEI_TING:
                    return "内廷";
                case AcupointNumber.XIAN_GU:
                    return "陷谷";
                case AcupointNumber.CHONG_YANG:
                    return "冲阳";
                case AcupointNumber.JIE_XI:
                    return "解溪";
                case AcupointNumber.FENG_LONG:
                    return "丰隆";
                case AcupointNumber.XIA_JU_XU:
                    return "上巨虚";
                case AcupointNumber.TIAO_KOU:
                    return "条口";
                case AcupointNumber.SHANG_JU_XU:
                    return "上巨虚";
                case AcupointNumber.ZU_SAN_LI:
                    return "足三里";
                case AcupointNumber.DU_BI:
                    return "犊鼻";
                case AcupointNumber.LIANG_QIU:
                    return "梁丘";

                case AcupointNumber.LENG_QING_YUAN:
                    return "清冷渊";
                case AcupointNumber.TIAN_JING:
                    return "天井";
                case AcupointNumber.SI_DU:
                    return "四渎";
                case AcupointNumber.SAN_YANG_LUO:
                    return "三阳络";
                case AcupointNumber.HUI_ZONG:
                    return "会宗";
                case AcupointNumber.ZHI_GOU:
                    return "支沟";
                case AcupointNumber.WAI_GUAN:
                    return "外关";
                case AcupointNumber.YANG_CHI:
                    return "阳池";
                case AcupointNumber.ZHONG_ZHU:
                    return "中渚";
                case AcupointNumber.YE_MEN:
                    return "夜门";
                case AcupointNumber.GUAN_CHONG:
                    return "关冲";

                case AcupointNumber.XIAO_HAI:
                    return "小海";
                case AcupointNumber.ZHI_ZHENG:
                    return "支正";
                case AcupointNumber.YANG_LAO:
                    return "养老";
                case AcupointNumber.YANG_GU:
                    return "阳谷";
                case AcupointNumber.WAN_GU:
                    return "腕谷";
                case AcupointNumber.HOU_XI:
                    return "后溪";
                case AcupointNumber.QIAN_GU:
                    return "前谷";
                case AcupointNumber.SHAO_ZE:
                    return "少泽";

                case AcupointNumber.FU_XI:
                    return "浮郄";
                case AcupointNumber.WEI_ZHONG:
                    return "委中";
                case AcupointNumber.HE_YANG:
                    return "合阳";
                case AcupointNumber.CHEN_JING:
                    return "承筋";
                case AcupointNumber.CHEN_SHAN:
                    return "承山";
                case AcupointNumber.FEI_YANG:
                    return "飞扬";
                case AcupointNumber.FU_YANG:
                    return "跗阳";
                case AcupointNumber.KUN_LUN:
                    return "昆仑";
                case AcupointNumber.PU_CAN:
                    return "仆参";
                case AcupointNumber.JIN_MEN:
                    return "金门";
                case AcupointNumber.SHU_GU:
                    return "束骨";
                case AcupointNumber.ZHI_YIN:
                    return "至阴";

                case AcupointNumber.QU_ZE:
                    return "曲泽";
                case AcupointNumber.XI_MEN:
                    return "郗门";
                case AcupointNumber.JIAN_SHI:
                    return "间使";
                case AcupointNumber.NEI_GUAN:
                    return "内关";
                case AcupointNumber.DA_LIN:
                    return "大陵";
                case AcupointNumber.LAO_GONG:
                    return "劳宫";
                case AcupointNumber.ZHONG_CHONG:
                    return "中冲";

                case AcupointNumber.QU_QUAN:
                    return "曲泉";
                case AcupointNumber.XI_GUAN:
                    return "膝关";
                case AcupointNumber.ZHONG_DU:
                    return "中都";
                case AcupointNumber.LI_GOU:
                    return "蠡沟";

            }
            return "";
        }

        /**
         * 根据上面的AcupintNumber来获取哪张穴位图展示
         *
         * @param acupointNumber 这个穴位值是我自己定义的，和真实的治疗仪返回值无关，需要转换
         * @return
         */
        public static int getAcupointDrawableResouces(int acupointNumber) {
            switch (acupointNumber) {
                case AcupointNumber.SHAO_SHANG:
                case AcupointNumber.YU_JI:
                case AcupointNumber.TAI_YUAN:
                case AcupointNumber.JING_QU:
                case AcupointNumber.LIE_QUE:
                case AcupointNumber.KONG_ZUI:
                case AcupointNumber.CHI_ZE:
                    if (App.getInstance().getLanguageType() == Language.LAN_CN) {
                        return R.mipmap.ic_acupoint19;
                    } else {
                        return R.mipmap.tw_ic_acupoint19;
                    }

                case AcupointNumber.DA_DUN:
                case AcupointNumber.XING_JIAN:
                case AcupointNumber.TAI_CHONG:
                case AcupointNumber.ZHONG_FENG:
                    if (App.getInstance().getLanguageType() == Language.LAN_CN) {
                        return R.mipmap.ic_acupoint20;
                    } else {
                        return R.mipmap.tw_ic_acupoint20;
                    }

                case AcupointNumber.YIN_GU:
                case AcupointNumber.ZHU_BIN:
                case AcupointNumber.JIAO_XIN:
                case AcupointNumber.FU_LIU:
                case AcupointNumber.TAI_XI:
                case AcupointNumber.SHUI_QUAN:
                case AcupointNumber.ZHAO_HAI:
                case AcupointNumber.RAN_GU:
                    if (App.getInstance().getLanguageType() == Language.LAN_CN) {
                        return R.mipmap.ic_acupoint21;
                    } else {
                        return R.mipmap.tw_ic_acupoint21;
                    }

                case AcupointNumber.QING_LIN:
                case AcupointNumber.SHAO_HAI:
                case AcupointNumber.LING_DAO:
                case AcupointNumber.TONG_LI:
                case AcupointNumber.YIN_XI:
                case AcupointNumber.SHEN_MEN:
                case AcupointNumber.SHAO_FU:
                    if (App.getInstance().getLanguageType() == Language.LAN_CN) {
                        return R.mipmap.ic_acupoint22;
                    } else {
                        return R.mipmap.tw_ic_acupoint22;
                    }

                case AcupointNumber.XI_YANG_GUAN:
                case AcupointNumber.YANG_LING_QUAN:
                case AcupointNumber.YANG_JIAO:
                case AcupointNumber.WAI_QIU:
                case AcupointNumber.GUANG_MING:
                case AcupointNumber.YANG_FU:
                case AcupointNumber.XUAN_ZHONG:
                case AcupointNumber.QIU_XU:
                case AcupointNumber.DI_WU_HUI:
                case AcupointNumber.ZU_LIN_QI:
                case AcupointNumber.XIA_XI:
                case AcupointNumber.ZU_QIAO_YIN:
                    if (App.getInstance().getLanguageType() == Language.LAN_CN) {
                        return R.mipmap.ic_acupoint23;
                    } else {
                        return R.mipmap.tw_ic_acupoint23;
                    }

                case AcupointNumber.SHANG_YANG:
                case AcupointNumber.ER_JIAN:
                case AcupointNumber.SAN_JIAN:
                case AcupointNumber.HE_GU:
                case AcupointNumber.YANG_GUAN:
                case AcupointNumber.BIAN_LI:
                case AcupointNumber.WEN_LIU:
                case AcupointNumber.XIA_LIAN:
                case AcupointNumber.SHANG_LIAN:
                case AcupointNumber.SHOU_SAN_LI:
                case AcupointNumber.QU_CHI:
                case AcupointNumber.ZHOU_LIAO:
                case AcupointNumber.SHOU_WU_LI:
                    if (App.getInstance().getLanguageType() == Language.LAN_CN) {
                        return R.mipmap.ic_acupoint24;
                    } else {
                        return R.mipmap.tw_ic_acupoint24;
                    }

                case AcupointNumber.LI_DUI:
                case AcupointNumber.NEI_TING:
                case AcupointNumber.XIAN_GU:
                case AcupointNumber.CHONG_YANG:
                case AcupointNumber.JIE_XI:
                case AcupointNumber.FENG_LONG:
                case AcupointNumber.XIA_JU_XU:
                case AcupointNumber.TIAO_KOU:
                case AcupointNumber.SHANG_JU_XU:
                case AcupointNumber.ZU_SAN_LI:
                case AcupointNumber.DU_BI:
                case AcupointNumber.LIANG_QIU:
                    if (App.getInstance().getLanguageType() == Language.LAN_CN) {
                        return R.mipmap.ic_acupoint25;
                    } else {
                        return R.mipmap.tw_ic_acupoint25;
                    }

                case AcupointNumber.LENG_QING_YUAN:
                case AcupointNumber.TIAN_JING:
                case AcupointNumber.SI_DU:
                case AcupointNumber.SAN_YANG_LUO:
                case AcupointNumber.HUI_ZONG:
                case AcupointNumber.ZHI_GOU:
                case AcupointNumber.WAI_GUAN:
                case AcupointNumber.YANG_CHI:
                case AcupointNumber.ZHONG_ZHU:
                case AcupointNumber.YE_MEN:
                case AcupointNumber.GUAN_CHONG:
                    if (App.getInstance().getLanguageType() == Language.LAN_CN) {
                        return R.mipmap.ic_acupoint26;
                    } else {
                        return R.mipmap.tw_ic_acupoint26;
                    }

                case AcupointNumber.XIAO_HAI:
                case AcupointNumber.ZHI_ZHENG:
                case AcupointNumber.YANG_LAO:
                case AcupointNumber.YANG_GU:
                case AcupointNumber.WAN_GU:
                case AcupointNumber.HOU_XI:
                case AcupointNumber.QIAN_GU:
                case AcupointNumber.SHAO_ZE:
                    if (App.getInstance().getLanguageType() == Language.LAN_CN) {
                        return R.mipmap.ic_acupoint27;
                    } else {
                        return R.mipmap.tw_ic_acupoint27;
                    }

                case AcupointNumber.FU_XI:
                case AcupointNumber.WEI_ZHONG:
                case AcupointNumber.HE_YANG:
                case AcupointNumber.CHEN_JING:
                case AcupointNumber.CHEN_SHAN:
                case AcupointNumber.FEI_YANG:
                case AcupointNumber.FU_YANG:
                case AcupointNumber.KUN_LUN:
                case AcupointNumber.PU_CAN:
                case AcupointNumber.JIN_MEN:
                case AcupointNumber.SHU_GU:
                case AcupointNumber.ZHI_YIN:
                    if (App.getInstance().getLanguageType() == Language.LAN_CN) {
                        return R.mipmap.ic_acupoint28;
                    } else {
                        return R.mipmap.tw_ic_acupoint28;
                    }

                case AcupointNumber.QU_ZE:
                case AcupointNumber.XI_MEN:
                case AcupointNumber.JIAN_SHI:
                case AcupointNumber.NEI_GUAN:
                case AcupointNumber.DA_LIN:
                case AcupointNumber.LAO_GONG:
                case AcupointNumber.ZHONG_CHONG:
                    if (App.getInstance().getLanguageType() == Language.LAN_CN) {
                        return R.mipmap.ic_acupoint29;
                    } else {
                        return R.mipmap.tw_ic_acupoint29;
                    }

                case AcupointNumber.QU_QUAN:
                case AcupointNumber.XI_GUAN:
                case AcupointNumber.ZHONG_DU:
                case AcupointNumber.LI_GOU:
                    if (App.getInstance().getLanguageType() == Language.LAN_CN) {
                        return R.mipmap.ic_acupoint30;
                    } else {
                        return R.mipmap.tw_ic_acupoint30;
                    }

            }
            return R.mipmap.ic_acupoint19;
        }

        /**
         * 根据AcupointNumber的值返回对应的图中穴位坐标
         *
         * @param acupointNumber
         * @return
         */
        public static int[] getAcupintCoordinate(int acupointNumber) {
            switch (acupointNumber) {
                case AcupointNumber.SHAO_SHANG:
                    return AcupointCoordinate.SHAO_SHANG;
                case AcupointNumber.YU_JI:
                    return AcupointCoordinate.YU_JI;
                case AcupointNumber.TAI_YUAN:
                    return AcupointCoordinate.TAI_YUAN;
                case AcupointNumber.JING_QU:
                    return AcupointCoordinate.JING_QU;
                case AcupointNumber.LIE_QUE:
                    return AcupointCoordinate.LIE_QUE;
                case AcupointNumber.KONG_ZUI:
                    return AcupointCoordinate.KONG_ZUI;
                case AcupointNumber.CHI_ZE:
                    return AcupointCoordinate.CHI_ZE;

                case AcupointNumber.DA_DUN:
                    return AcupointCoordinate.DA_DUN;
                case AcupointNumber.XING_JIAN:
                    return AcupointCoordinate.XING_JIAN;
                case AcupointNumber.TAI_CHONG:
                    return AcupointCoordinate.TAI_CHONG;
                case AcupointNumber.ZHONG_FENG:
                    return AcupointCoordinate.ZHONG_FENG;

                case AcupointNumber.YIN_GU:
                    return AcupointCoordinate.YIN_GU;
                case AcupointNumber.ZHU_BIN:
                    return AcupointCoordinate.ZHU_BIN;
                case AcupointNumber.JIAO_XIN:
                    return AcupointCoordinate.JIAO_XIN;
                case AcupointNumber.FU_LIU:
                    return AcupointCoordinate.FU_LIU;
                case AcupointNumber.TAI_XI:
                    return AcupointCoordinate.TAI_XI;
                case AcupointNumber.SHUI_QUAN:
                    return AcupointCoordinate.SHUI_QUAN;
                case AcupointNumber.ZHAO_HAI:
                    return AcupointCoordinate.ZHAO_HAI;
                case AcupointNumber.RAN_GU:
                    return AcupointCoordinate.RAN_GU;

                case AcupointNumber.QING_LIN:
                    return AcupointCoordinate.QING_LIN;
                case AcupointNumber.SHAO_HAI:
                    return AcupointCoordinate.SHAO_HAI;
                case AcupointNumber.LING_DAO:
                    return AcupointCoordinate.LING_DAO;
                case AcupointNumber.TONG_LI:
                    return AcupointCoordinate.TONG_LI;
                case AcupointNumber.YIN_XI:
                    return AcupointCoordinate.YIN_XI;
                case AcupointNumber.SHEN_MEN:
                    return AcupointCoordinate.SHEN_MEN;
                case AcupointNumber.SHAO_FU:
                    return AcupointCoordinate.SHAO_FU;

                case AcupointNumber.XI_YANG_GUAN:
                    return AcupointCoordinate.XI_YANG_GUAN;
                case AcupointNumber.YANG_LING_QUAN:
                    return AcupointCoordinate.YANG_LING_QUAN;
                case AcupointNumber.YANG_JIAO:
                    return AcupointCoordinate.YANG_JIAO;
                case AcupointNumber.WAI_QIU:
                    return AcupointCoordinate.WAI_QIU;
                case AcupointNumber.GUANG_MING:
                    return AcupointCoordinate.GUANG_MING;
                case AcupointNumber.YANG_FU:
                    return AcupointCoordinate.YANG_FU;
                case AcupointNumber.XUAN_ZHONG:
                    return AcupointCoordinate.XUAN_ZHONG;
                case AcupointNumber.QIU_XU:
                    return AcupointCoordinate.QIU_XU;
                case AcupointNumber.DI_WU_HUI:
                    return AcupointCoordinate.DI_WU_HUI;
                case AcupointNumber.ZU_LIN_QI:
                    return AcupointCoordinate.ZU_LIN_QI;
                case AcupointNumber.XIA_XI:
                    return AcupointCoordinate.XIA_XI;
                case AcupointNumber.ZU_QIAO_YIN:
                    return AcupointCoordinate.ZU_QIAO_YIN;

                case AcupointNumber.SHANG_YANG:
                    return AcupointCoordinate.SHANG_YANG;
                case AcupointNumber.ER_JIAN:
                    return AcupointCoordinate.ER_JIAN;
                case AcupointNumber.SAN_JIAN:
                    return AcupointCoordinate.SAN_JIAN;
                case AcupointNumber.HE_GU:
                    return AcupointCoordinate.HE_GU;
                case AcupointNumber.YANG_GUAN:
                    return AcupointCoordinate.YANG_GUAN;
                case AcupointNumber.BIAN_LI:
                    return AcupointCoordinate.BIAN_LI;
                case AcupointNumber.WEN_LIU:
                    return AcupointCoordinate.WEN_LIU;
                case AcupointNumber.XIA_LIAN:
                    return AcupointCoordinate.XIA_LIAN;
                case AcupointNumber.SHANG_LIAN:
                    return AcupointCoordinate.SHANG_LIAN;
                case AcupointNumber.SHOU_SAN_LI:
                    return AcupointCoordinate.SHOU_SAN_LI;
                case AcupointNumber.QU_CHI:
                    return AcupointCoordinate.QU_CHI;
                case AcupointNumber.ZHOU_LIAO:
                    return AcupointCoordinate.ZHOU_LIAO;
                case AcupointNumber.SHOU_WU_LI:
                    return AcupointCoordinate.SHOU_WU_LI;

                case AcupointNumber.LI_DUI:
                    return AcupointCoordinate.LI_DUI;
                case AcupointNumber.NEI_TING:
                    return AcupointCoordinate.NEI_TING;
                case AcupointNumber.XIAN_GU:
                    return AcupointCoordinate.XIAN_GU;
                case AcupointNumber.CHONG_YANG:
                    return AcupointCoordinate.CHONG_YANG;
                case AcupointNumber.JIE_XI:
                    return AcupointCoordinate.JIE_XI;
                case AcupointNumber.FENG_LONG:
                    return AcupointCoordinate.FENG_LONG;
                case AcupointNumber.XIA_JU_XU:
                    return AcupointCoordinate.XIA_JU_XU;
                case AcupointNumber.TIAO_KOU:
                    return AcupointCoordinate.TIAO_KOU;
                case AcupointNumber.SHANG_JU_XU:
                    return AcupointCoordinate.SHANG_JU_XU;
                case AcupointNumber.ZU_SAN_LI:
                    return AcupointCoordinate.ZU_SAN_LI;
                case AcupointNumber.DU_BI:
                    return AcupointCoordinate.DU_BI;
                case AcupointNumber.LIANG_QIU:
                    return AcupointCoordinate.LIANG_QIU;

                case AcupointNumber.LENG_QING_YUAN:
                    return AcupointCoordinate.LENG_QING_YUAN;
                case AcupointNumber.TIAN_JING:
                    return AcupointCoordinate.TIAN_JING;
                case AcupointNumber.SI_DU:
                    return AcupointCoordinate.SI_DU;
                case AcupointNumber.SAN_YANG_LUO:
                    return AcupointCoordinate.SAN_YANG_LUO;
                case AcupointNumber.HUI_ZONG:
                    return AcupointCoordinate.HUI_ZONG;
                case AcupointNumber.ZHI_GOU:
                    return AcupointCoordinate.ZHI_GOU;
                case AcupointNumber.WAI_GUAN:
                    return AcupointCoordinate.WAI_GUAN;
                case AcupointNumber.YANG_CHI:
                    return AcupointCoordinate.YANG_CHI;
                case AcupointNumber.ZHONG_ZHU:
                    return AcupointCoordinate.ZHONG_ZHU;
                case AcupointNumber.YE_MEN:
                    return AcupointCoordinate.YE_MEN;
                case AcupointNumber.GUAN_CHONG:
                    return AcupointCoordinate.GUAN_CHONG;

                case AcupointNumber.XIAO_HAI:
                    return AcupointCoordinate.XIAO_HAI;
                case AcupointNumber.ZHI_ZHENG:
                    return AcupointCoordinate.ZHI_ZHENG;
                case AcupointNumber.YANG_LAO:
                    return AcupointCoordinate.YANG_LAO;
                case AcupointNumber.YANG_GU:
                    return AcupointCoordinate.YANG_GU;
                case AcupointNumber.WAN_GU:
                    return AcupointCoordinate.WAN_GU;
                case AcupointNumber.HOU_XI:
                    return AcupointCoordinate.HOU_XI;
                case AcupointNumber.QIAN_GU:
                    return AcupointCoordinate.QIAN_GU;
                case AcupointNumber.SHAO_ZE:
                    return AcupointCoordinate.SHAO_ZE;

                case AcupointNumber.FU_XI:
                    return AcupointCoordinate.FU_XI;
                case AcupointNumber.WEI_ZHONG:
                    return AcupointCoordinate.WEI_ZHONG;
                case AcupointNumber.HE_YANG:
                    return AcupointCoordinate.HE_YANG;
                case AcupointNumber.CHEN_JING:
                    return AcupointCoordinate.CHEN_JING;
                case AcupointNumber.CHEN_SHAN:
                    return AcupointCoordinate.CHEN_SHAN;
                case AcupointNumber.FEI_YANG:
                    return AcupointCoordinate.FEI_YANG;
                case AcupointNumber.FU_YANG:
                    return AcupointCoordinate.FU_YANG;
                case AcupointNumber.KUN_LUN:
                    return AcupointCoordinate.KUN_LUN;
                case AcupointNumber.PU_CAN:
                    return AcupointCoordinate.PU_CAN;
                case AcupointNumber.JIN_MEN:
                    return AcupointCoordinate.JIN_MEN;
                case AcupointNumber.SHU_GU:
                    return AcupointCoordinate.SHU_GU;
                case AcupointNumber.ZHI_YIN:
                    return AcupointCoordinate.ZHI_YIN;

                case AcupointNumber.QU_ZE:
                    return AcupointCoordinate.QU_ZE;
                case AcupointNumber.XI_MEN:
                    return AcupointCoordinate.XI_MEN;
                case AcupointNumber.JIAN_SHI:
                    return AcupointCoordinate.JIAN_SHI;
                case AcupointNumber.NEI_GUAN:
                    return AcupointCoordinate.NEI_GUAN;
                case AcupointNumber.DA_LIN:
                    return AcupointCoordinate.DA_LIN;
                case AcupointNumber.LAO_GONG:
                    return AcupointCoordinate.LAO_GONG;
                case AcupointNumber.ZHONG_CHONG:
                    return AcupointCoordinate.ZHONG_CHONG;

                case AcupointNumber.QU_QUAN:
                    return AcupointCoordinate.QU_QUAN;
                case AcupointNumber.XI_GUAN:
                    return AcupointCoordinate.XI_GUAN;
                case AcupointNumber.ZHONG_DU:
                    return AcupointCoordinate.ZHONG_DU;
                case AcupointNumber.LI_GOU:
                    return AcupointCoordinate.LI_GOU;

            }
            return null;
        }

        /**
         * 根据治疗仪返回的值得到一个Acupint Number对应的穴位值，这个值再获取图片和坐标
         * <p>
         * 01三阴交；02劳宫 ；03跗阳；04养老；05外关；06足三里；07手三里
         * 08悬钟；09少府；10然谷；11中封；12鱼际；13阴陵泉；14内关；15承山
         * 16小海；17四渎； 18丰隆；19合谷；20光明； 21少海； 22复溜； 23膝关；
         * 24孔最； 25漏谷；26郄门； 27合阳； 28腕谷； 29阳池； 30阑尾； 31曲池
         * 32足临泣； 33神门； 34筑宾；35中都； 36太渊；37公孙；38大陵；39昆仑
         * 40支正；41三阳络；42冲阳； 43温溜； 44阳陵泉；45灵道； 46水泉； 47太冲
         * 48尺泽； 49地机； 50曲泽；51委中； 52后溪； 53中渚； 54膝眼； 55阳溪
         * 56阳交； 57阴郄； 58阴谷； 59蠡沟； 60经渠
         *
         * @param deviceReturnAcupoint
         * @return
         */
        // 2019.2.1 对于ERROR_POINT统一返回少商JING_QU穴位
        public static int getAcupointNumberByDeviceReturnPoint(int deviceReturnAcupoint) {
            switch (deviceReturnAcupoint) {
                case 1:
                    return AcupointNumber.JING_QU;
                case 2:
                    return AcupointNumber.LAO_GONG;
                case 3:
                    return AcupointNumber.FU_YANG;
                case 4:
                    return AcupointNumber.YANG_LAO;
                case 5:
                    return AcupointNumber.WAI_GUAN;
                case 6:
                    return AcupointNumber.ZU_SAN_LI;
                case 7:
                    return AcupointNumber.SHOU_SAN_LI;
                case 8:
                    return AcupointNumber.XUAN_ZHONG;
                case 9:
                    return AcupointNumber.SHAO_FU;
                case 10:
                    return AcupointNumber.RAN_GU;

                case 11:
                    return AcupointNumber.ZHONG_FENG;
                case 12:
                    return AcupointNumber.YU_JI;
                case 13:
                    return AcupointNumber.JING_QU;
                case 14:
                    return AcupointNumber.NEI_GUAN;
                case 15:
                    return AcupointNumber.CHEN_SHAN;
                case 16:
                    return AcupointNumber.XIAO_HAI;
                case 17:
                    return AcupointNumber.SI_DU;
                case 18:
                    return AcupointNumber.FENG_LONG;
                case 19:
                    return AcupointNumber.HE_GU;
                case 20:
                    return AcupointNumber.GUANG_MING;

                case 21:
                    return AcupointNumber.SHAO_HAI;
                case 22:
                    return AcupointNumber.FU_LIU;
                case 23:
                    return AcupointNumber.XI_GUAN;
                case 24:
                    return AcupointNumber.KONG_ZUI;
                case 25:
                    return AcupointNumber.JING_QU;
                case 26:
                    return AcupointNumber.XI_MEN;
                case 27:
                    return AcupointNumber.HE_YANG;
                case 28:
                    return AcupointNumber.WAN_GU;
                case 29:
                    return AcupointNumber.YANG_CHI;
                case 30:
                    return AcupointNumber.JING_QU;

                case 31:
                    return AcupointNumber.QU_CHI;
                case 32:
                    return AcupointNumber.ZU_LIN_QI;
                case 33:
                    return AcupointNumber.SHEN_MEN;
                case 34:
                    return AcupointNumber.ZHU_BIN;
                case 35:
                    return AcupointNumber.ZHONG_DU;
                case 36:
                    return AcupointNumber.TAI_YUAN;
                case 37:
                    return AcupointNumber.JING_QU;
                case 38:
                    return AcupointNumber.DA_LIN;
                case 39:
                    return AcupointNumber.KUN_LUN;
                case 40:
                    return AcupointNumber.ZHI_ZHENG;

                case 41:
                    return AcupointNumber.SAN_YANG_LUO;
                case 42:
                    return AcupointNumber.CHONG_YANG;
                case 43:
                    return AcupointNumber.WEN_LIU;
                case 44:
                    return AcupointNumber.YANG_LING_QUAN;
                case 45:
                    return AcupointNumber.LING_DAO;
                case 46:
                    return AcupointNumber.SHUI_QUAN;
                case 47:
                    return AcupointNumber.TAI_CHONG;
                case 48:
                    return AcupointNumber.CHI_ZE;
                case 49:
                    return AcupointNumber.JING_QU;
                case 50:
                    return AcupointNumber.QU_ZE;

                case 51:
                    return AcupointNumber.WEI_ZHONG;
                case 52:
                    return AcupointNumber.HOU_XI;
                case 53:
                    return AcupointNumber.ZHONG_ZHU;
                case 54:
                    return AcupointNumber.XI_YANG_GUAN;
                case 55:
                    return AcupointNumber.JING_QU;
                case 56:
                    return AcupointNumber.YANG_JIAO;
                case 57:
                    return AcupointNumber.YIN_XI;
                case 58:
                    return AcupointNumber.YIN_GU;
                case 59:
                    return AcupointNumber.LI_GOU;
                case 60:
                    return AcupointNumber.JING_QU;
            }

            return AcupointNumber.JING_QU;
        }

        /**
         * 一：穴位名称简介如下：
         * {"三阴交，位于小腿内侧，踝关节上三横指处。"},
         * {"劳宫，掌心向上，位于手掌心中央处。"},
         * {"跗阳，位于小腿外侧踝关节向上三横指处。"},
         * {"养老，掌心朝向肩关节方向屈肘，位于手背腕横纹内侧凹陷处。"},
         * {"外关，掌心向下，位于手背腕横纹中点向上二横指处。"},
         * {"足三里，位于膝关节外侧向下四横指处。"},
         * {"手三里，掌心朝下，位于肘关节向下二横指处。"},
         * {"悬钟，位于外踝关节直上二横指处。"},
         * {"少府，掌心朝上，位于小鱼际中心处。"},
         * {"然谷，位于内侧踝关节向前斜下方三横指处。"},
         * {"中封，位于足背内侧，足腕关节处。"},
         * {"鱼际，掌心向上，位于大鱼际中心处。"},
         * {"阴陵泉，位于在小腿内侧，膝关节下凹陷处。"},
         * {"内关，掌心向上，位于手腕横纹的中点向上二横指处。"},
         * {"承山，位于小腿后侧腓肠肌中点处。"},
         * {"小海，屈肘，位于肘关节靠尺骨鹰嘴凹陷处。"},
         * {"四渎，掌心向下，位于手背腕横纹中点向上五横指处。"},
         * {"丰隆，位于膝关节外侧膝眼和外踝关节的连线中点处。"},
         * {"合谷，将拇指和食指张开，位于虎口的拇指与食指交界的凹陷处。"},
         * {"光明，位于外踝关节直上五横指处。"},
         * {"少海，掌心朝上，位于肘关节内侧凹陷处。"},
         * {"复溜，位于足内侧踝关节上二横指处。"},
         * {"膝关，屈膝，位于膝关节内侧向后下二横指处。"},
         * {"孔最，掌心向上，位于手腕横纹外侧向上七横指处。"},
         * {"漏谷，位于小腿内侧，踝关节上五横指处。"},
         * {"郄门，掌心向上，位于手腕横纹向上五横指处。"},
         * {"合阳，位于大腿后侧腘窝中点向下二横指处。"},
         * {"腕谷，掌心朝下，位于手腕横纹外侧凹陷处。"},
         * {"阳池，掌心向下，位于手背腕横纹中点处。"},
         * {"兰尾，位于小腿外侧，膝关节下五横指处。"},
         * {"曲池，屈肘关节，位于肘关节弯中心处。"},
         * {"足临泣，位于足背外侧边中点位处。"},
         * {"神门，掌心向上，位于小鱼际根部的手腕横纹外侧处。"},
         * {"筑宾，位于足内侧踝关节上五横指处。"},
         * {"中都，位于内踝关节直上七横指处。"},
         * {"太渊，掌心向上，位于大鱼际根部手腕横纹处。"},
         * {"公孙，位于足大指掌指关节内侧后一横指处。"},
         * {"大陵，掌心向上，位于手腕横纹正中点处。"},
         * {"昆仑，位于外侧踝关节顶点与脚跟连线的中点处。"},
         * {"支正，掌心朝下，位于腕横纹外侧向上五横指处。"},
         * {"三阳络，掌心向下，位于手背腕横纹中点向上四横指处。"},
         * {"冲阳，位于足背与小腿折弯中点处。"},
         * {"温溜，掌心朝下，位于手腕横纹内侧向上五横指处。"},
         * {"阳陵泉，屈膝，位于膝盖外侧斜下方三横指处。"},
         * {"灵道，掌心向上，位于小鱼际根部手腕横纹外侧向上二横指处。"},
         * {"水泉，位于足内侧踝下。"},
         * {"太冲，位于足背第一与第二掌骨间中点处。"},
         * {"尺泽，掌心向上，位于肘关节内侧中点处。"},
         * {"地机，位于小腿内侧，膝关节下五横指处。"},
         * {"曲泽，掌心向上，位于肘关节肘横纹内侧处。"},
         * {"委中，位于大腿后侧腘窝中点处。"},
         * {"后溪，掌心朝下，位于手掌外侧边中点位处。"},
         * {"中渚，掌心向下，位于手背第四与第五掌骨间中点凹陷处。"},
         * {"膝眼，屈膝，位于膝盖骨外下方凹陷处。"},
         * {"阳溪，掌心朝下，位于手腕内侧，拇指根手腕凹陷处。"},
         * {"阳交，位于外踝关节直上七横指处。"},
         * {"阴郄，掌心向上，位于手腕横线外侧向上0.5公分处。"},
         * {"阴谷，位于大腿后侧腘窝内侧边处。"},
         * {"蠡沟，位于内踝关节直上五横指处。"},
         * {"经渠，掌心向上，位于手腕横纹外侧向上一横指，挠动脉处。"}
         * @param deviceReturnPoint
         * @return
         */
        public static String getAcupointDescByDeviceReturnPoint(int deviceReturnPoint) {
            switch (deviceReturnPoint) {
                case 1:
                    return "经渠，掌心向上，位于手腕横纹外侧向上一横指，挠动脉处";
                case 2:
                    return "劳宫，掌心向上，位于手掌心中央处";
                case 3:
                    return "跗阳，位于小腿外侧踝关节向上三横指处";
                case 4:
                    return "养老，掌心朝向肩关节方向屈肘，位于手背腕横纹内侧凹陷处";
                case 5:
                    return "外关，掌心向下，位于手背腕横纹中点向上二横指处";
                case 6:
                    return "足三里，位于膝关节外侧向下四横指处";
                case 7:
                    return "手三里，掌心朝下，位于肘关节向下二横指处";
                case 8:
                    return "悬钟，位于外踝关节直上二横指处";
                case 9:
                    return "少府，掌心朝上，位于小鱼际中心处";
                case 10:
                    return "然谷，位于内侧踝关节向前斜下方三横指处";

                case 11:
                    return "中封，位于足背内侧，足腕关节处";
                case 12:
                    return "鱼际，掌心向上，位于大鱼际中心处";
                case 13:
                    return "经渠，掌心向上，位于手腕横纹外侧向上一横指，挠动脉处";
                case 14:
                    return "内关，掌心向上，位于手腕横纹的中点向上二横指处";
                case 15:
                    return "承山，位于小腿后侧腓肠肌中点处";
                case 16:
                    return "小海，屈肘，位于肘关节靠尺骨鹰嘴凹陷处";
                case 17:
                    return "四渎，掌心向下，位于手背腕横纹中点向上五横指处";
                case 18:
                    return "丰隆，位于膝关节外侧膝眼和外踝关节的连线中点处";
                case 19:
                    return "合谷，将拇指和食指张开，位于虎口的拇指与食指交界的凹陷处";
                case 20:
                    return "光明，位于外踝关节直上五横指处";

                case 21:
                    return "少海，掌心朝上，位于肘关节内侧凹陷处";
                case 22:
                    return "复溜，位于足内侧踝关节上二横指处";
                case 23:
                    return "膝关，屈膝，位于膝关节内侧向后下二横指处";
                case 24:
                    return "孔最，掌心向上，位于手腕横纹外侧向上七横指处";
                case 25:
                    return "经渠，掌心向上，位于手腕横纹外侧向上一横指，挠动脉处";
                case 26:
                    return "郄门，掌心向上，位于手腕横纹向上五横指处";
                case 27:
                    return "合阳，位于大腿后侧腘窝中点向下二横指处";
                case 28:
                    return "腕谷，掌心朝下，位于手腕横纹外侧凹陷处";
                case 29:
                    return "阳池，掌心向下，位于手背腕横纹中点处";
                case 30:
                    return "经渠，掌心向上，位于手腕横纹外侧向上一横指，挠动脉处";

                case 31:
                    return "曲池，屈肘关节，位于肘关节弯中心处";
                case 32:
                    return "足临泣，位于足背外侧边中点位处";
                case 33:
                    return "神门，掌心向上，位于小鱼际根部的手腕横纹外侧处";
                case 34:
                    return "筑宾，位于足内侧踝关节上五横指处";
                case 35:
                    return "中都，位于内踝关节直上七横指处";
                case 36:
                    return "太渊，掌心向上，位于大鱼际根部手腕横纹处";
                case 37:
                    return "经渠，掌心向上，位于手腕横纹外侧向上一横指，挠动脉处";
                case 38:
                    return "大陵，掌心向上，位于手腕横纹正中点处";
                case 39:
                    return "昆仑，位于外侧踝关节顶点与脚跟连线的中点处";
                case 40:
                    return "支正，掌心朝下，位于腕横纹外侧向上五横指处";

                case 41:
                    return "三阳络，掌心向下，位于手背腕横纹中点向上四横指处";
                case 42:
                    return "冲阳，位于足背与小腿折弯中点处";
                case 43:
                    return "温溜，掌心朝下，位于手腕横纹内侧向上五横指处";
                case 44:
                    return "阳陵泉，屈膝，位于膝盖外侧斜下方三横指处";
                case 45:
                    return "灵道，掌心向上，位于小鱼际根部手腕横纹外侧向上二横指处";
                case 46:
                    return "水泉，位于足内侧踝下";
                case 47:
                    return "太冲，位于足背第一与第二掌骨间中点处";
                case 48:
                    return "尺泽，掌心向上，位于肘关节内侧中点处";
                case 49:
                    return "经渠，掌心向上，位于手腕横纹外侧向上一横指，挠动脉处";
                case 50:
                    return "曲泽，掌心向上，位于肘关节肘横纹内侧处";

                case 51:
                    return "委中，位于大腿后侧腘窝中点处";
                case 52:
                    return "后溪，掌心朝下，位于手掌外侧边中点位处";
                case 53:
                    return "中渚，掌心向下，位于手背第四与第五掌骨间中点凹陷处";
                case 54:
                    return "膝眼，屈膝，位于膝盖骨外下方凹陷处";
                case 55:
                    return "经渠，掌心向上，位于手腕横纹外侧向上一横指，挠动脉处";
                case 56:
                    return "阳交，位于外踝关节直上七横指处";
                case 57:
                    return "阴郄，掌心向上，位于手腕横线外侧向上0.5公分处";
                case 58:
                    return "阴谷，位于大腿后侧腘窝内侧边处";
                case 59:
                    return "蠡沟，位于内踝关节直上五横指处";
                case 60:
                    return "经渠，掌心向上，位于手腕横纹外侧向上一横指，挠动脉处";
            }

            return "经渠，掌心向上，位于手腕横纹外侧向上一横指，挠动脉处";
        }

    }

    public static final String ZCODE_SERVER_TEXT = "[{\"zcode\":86,\"zname\":\"\\u4e2d\\u56fd\"},{\"zcode\":1,\"zname\":\"\\u7f8e\\u56fd\"},{\"zcode\":1,\"zname\":\"\\u52a0\\u62ff\\u5927\"},{\"zcode\":7,\"zname\":\"\\u54c8\\u8428\\u514b\\u65af\\u5766\"},{\"zcode\":7,\"zname\":\"\\u4fc4\\u7f57\\u65af\"},{\"zcode\":20,\"zname\":\"\\u57c3\\u53ca\"},{\"zcode\":27,\"zname\":\"\\u5357\\u975e\"},{\"zcode\":30,\"zname\":\"\\u5e0c\\u814a\"},{\"zcode\":31,\"zname\":\"\\u8377\\u5170\"},{\"zcode\":32,\"zname\":\"\\u6bd4\\u5229\\u65f6\"},{\"zcode\":33,\"zname\":\"\\u6cd5\\u56fd\"},{\"zcode\":34,\"zname\":\"\\u897f\\u73ed\\u7259\"},{\"zcode\":36,\"zname\":\"\\u5308\\u7259\\u5229\"},{\"zcode\":39,\"zname\":\"\\u610f\\u5927\\u5229\"},{\"zcode\":40,\"zname\":\"\\u7f57\\u9a6c\\u5c3c\\u4e9a\"},{\"zcode\":41,\"zname\":\"\\u745e\\u58eb\"},{\"zcode\":43,\"zname\":\"\\u5965\\u5730\\u5229\"},{\"zcode\":44,\"zname\":\"\\u82f1\\u56fd\"},{\"zcode\":45,\"zname\":\"\\u4e39\\u9ea6\"},{\"zcode\":46,\"zname\":\"\\u745e\\u5178\"},{\"zcode\":47,\"zname\":\"\\u632a\\u5a01\"},{\"zcode\":48,\"zname\":\"\\u6ce2\\u5170\"},{\"zcode\":49,\"zname\":\"\\u5fb7\\u56fd\"},{\"zcode\":51,\"zname\":\"\\u79d8\\u9c81\"},{\"zcode\":52,\"zname\":\"\\u58a8\\u897f\\u54e5\"},{\"zcode\":53,\"zname\":\"\\u53e4\\u5df4\"},{\"zcode\":54,\"zname\":\"\\u963f\\u6839\\u5ef7\"},{\"zcode\":55,\"zname\":\"\\u5df4\\u897f\"},{\"zcode\":56,\"zname\":\"\\u667a\\u5229\"},{\"zcode\":57,\"zname\":\"\\u54e5\\u4f26\\u6bd4\\u4e9a\"},{\"zcode\":58,\"zname\":\"\\u59d4\\u5185\\u745e\\u62c9\"},{\"zcode\":60,\"zname\":\"\\u9a6c\\u6765\\u897f\\u4e9a\"},{\"zcode\":61,\"zname\":\"\\u6fb3\\u5927\\u5229\\u4e9a\"},{\"zcode\":62,\"zname\":\"\\u5370\\u5ea6\\u5c3c\\u897f\\u4e9a\"},{\"zcode\":63,\"zname\":\"\\u83f2\\u5f8b\\u5bbe\"},{\"zcode\":64,\"zname\":\"\\u65b0\\u897f\\u5170\"},{\"zcode\":65,\"zname\":\"\\u65b0\\u52a0\\u5761\"},{\"zcode\":66,\"zname\":\"\\u6cf0\\u56fd\"},{\"zcode\":81,\"zname\":\"\\u65e5\\u672c\"},{\"zcode\":82,\"zname\":\"\\u97e9\\u56fd\"},{\"zcode\":84,\"zname\":\"\\u8d8a\\u5357\"},{\"zcode\":90,\"zname\":\"\\u571f\\u8033\\u5176\"},{\"zcode\":91,\"zname\":\"\\u5370\\u5ea6\"},{\"zcode\":92,\"zname\":\"\\u5df4\\u52d2\\u65af\\u5766\"},{\"zcode\":93,\"zname\":\"\\u963f\\u5bcc\\u6c57\"},{\"zcode\":94,\"zname\":\"\\u65af\\u91cc\\u5170\\u5361\"},{\"zcode\":95,\"zname\":\"\\u7f05\\u7538\"},{\"zcode\":98,\"zname\":\"\\u4f0a\\u6717\"},{\"zcode\":211,\"zname\":\"\\u82cf\\u4e39\"},{\"zcode\":212,\"zname\":\"\\u6469\\u6d1b\\u54e5\"},{\"zcode\":213,\"zname\":\"\\u963f\\u5c14\\u53ca\\u5229\\u4e9a\"},{\"zcode\":216,\"zname\":\"\\u7a81\\u5c3c\\u65af\"},{\"zcode\":218,\"zname\":\"\\u5229\\u6bd4\\u4e9a\"},{\"zcode\":220,\"zname\":\"\\u5188\\u6bd4\\u4e9a\"},{\"zcode\":221,\"zname\":\"\\u585e\\u5185\\u52a0\\u5c14\"},{\"zcode\":222,\"zname\":\"\\u6bdb\\u91cc\\u5854\\u5c3c\\u4e9a\"},{\"zcode\":223,\"zname\":\"\\u9a6c\\u91cc\"},{\"zcode\":224,\"zname\":\"\\u5df4\\u5e03\\u4e9a\\u65b0\\u51e0\\u5185\\u4e9a\"},{\"zcode\":226,\"zname\":\"\\u5e03\\u57fa\\u7eb3\\u6cd5\\u7d22\"},{\"zcode\":227,\"zname\":\"\\u5c3c\\u65e5\\u5c14\"},{\"zcode\":228,\"zname\":\"\\u591a\\u54e5\"},{\"zcode\":229,\"zname\":\"\\u8d1d\\u5b81\\u5171\\u548c\\u56fd\"},{\"zcode\":230,\"zname\":\"\\u6bdb\\u91cc\\u6c42\\u65af\"},{\"zcode\":231,\"zname\":\"\\u5229\\u6bd4\\u91cc\\u4e9a\"},{\"zcode\":232,\"zname\":\"\\u585e\\u62c9\\u5229\\u6602\"},{\"zcode\":233,\"zname\":\"\\u52a0\\u7eb3\"},{\"zcode\":234,\"zname\":\"\\u5c3c\\u65e5\\u5229\\u4e9a\"},{\"zcode\":235,\"zname\":\"\\u4e4d\\u5f97\"},{\"zcode\":236,\"zname\":\"\\u4e2d\\u975e\\u5171\\u548c\\u56fd\"},{\"zcode\":237,\"zname\":\"\\u5580\\u9ea6\\u9686\"},{\"zcode\":238,\"zname\":\"\\u4f5b\\u5f97\\u89d2\"},{\"zcode\":239,\"zname\":\"\\u5723\\u591a\\u7f8e\\u548c\\u666e\\u6797\\u897f\\u6bd4\"},{\"zcode\":240,\"zname\":\"\\u8d64\\u9053\\u51e0\\u5185\\u4e9a\"},{\"zcode\":241,\"zname\":\"\\u52a0\\u84ec\"},{\"zcode\":242,\"zname\":\"\\u521a\\u679c\"},{\"zcode\":243,\"zname\":\"\\u521a\\u679c\\u6c11\\u4e3b\\u5171\\u548c\\u56fd\"},{\"zcode\":244,\"zname\":\"\\u5b89\\u54e5\\u62c9\"},{\"zcode\":245,\"zname\":\"\\u51e0\\u5185\\u4e9a\\u6bd4\\u7ecd\\u5171\\u548c\\u56fd\"},{\"zcode\":248,\"zname\":\"\\u585e\\u820c\\u5c14\"},{\"zcode\":250,\"zname\":\"\\u5362\\u65fa\\u8fbe\"},{\"zcode\":251,\"zname\":\"\\u57c3\\u585e\\u4fc4\\u6bd4\\u4e9a\"},{\"zcode\":252,\"zname\":\"\\u7d22\\u9a6c\\u91cc\"},{\"zcode\":253,\"zname\":\"\\u5409\\u5e03\\u63d0\"},{\"zcode\":254,\"zname\":\"\\u80af\\u5c3c\\u4e9a\"},{\"zcode\":255,\"zname\":\"\\u5766\\u6851\\u5c3c\\u4e9a\"},{\"zcode\":256,\"zname\":\"\\u4e4c\\u5e72\\u8fbe\"},{\"zcode\":257,\"zname\":\"\\u5e03\\u9686\\u8fea\"},{\"zcode\":258,\"zname\":\"\\u83ab\\u6851\\u6bd4\\u514b\"},{\"zcode\":260,\"zname\":\"\\u8d5e\\u6bd4\\u4e9a\"},{\"zcode\":261,\"zname\":\"\\u9a6c\\u8fbe\\u52a0\\u65af\\u52a0\"},{\"zcode\":262,\"zname\":\"\\u7559\\u5c3c\\u65fa\\u5c9b\"},{\"zcode\":263,\"zname\":\"\\u6d25\\u5df4\\u5e03\\u97e6\"},{\"zcode\":264,\"zname\":\"\\u7eb3\\u7c73\\u6bd4\\u4e9a\"},{\"zcode\":265,\"zname\":\"\\u9a6c\\u62c9\\u7ef4\"},{\"zcode\":266,\"zname\":\"\\u83b1\\u7d22\\u6258\"},{\"zcode\":267,\"zname\":\"\\u535a\\u8328\\u74e6\\u7eb3\"},{\"zcode\":268,\"zname\":\"\\u65af\\u5a01\\u58eb\\u5170\"},{\"zcode\":291,\"zname\":\"\\u5384\\u7acb\\u7279\\u91cc\\u4e9a\"},{\"zcode\":297,\"zname\":\"\\u963f\\u9c81\\u5df4\"},{\"zcode\":298,\"zname\":\"\\u6cd5\\u7f57\\u7fa4\\u5c9b\"},{\"zcode\":350,\"zname\":\"\\u76f4\\u5e03\\u7f57\\u9640\"},{\"zcode\":351,\"zname\":\"\\u8461\\u8404\\u7259\"},{\"zcode\":352,\"zname\":\"\\u5362\\u68ee\\u5821\"},{\"zcode\":353,\"zname\":\"\\u7231\\u5c14\\u5170\"},{\"zcode\":354,\"zname\":\"\\u51b0\\u5c9b\"},{\"zcode\":355,\"zname\":\"\\u963f\\u5c14\\u5df4\\u5c3c\\u4e9a\"},{\"zcode\":356,\"zname\":\"\\u9a6c\\u8033\\u4ed6\"},{\"zcode\":357,\"zname\":\"\\u585e\\u6d66\\u8def\\u65af\"},{\"zcode\":358,\"zname\":\"\\u82ac\\u5170\"},{\"zcode\":359,\"zname\":\"\\u4fdd\\u52a0\\u5229\\u4e9a\"},{\"zcode\":370,\"zname\":\"\\u7acb\\u9676\\u5b9b\"},{\"zcode\":371,\"zname\":\"\\u62c9\\u8131\\u7ef4\\u4e9a\"},{\"zcode\":372,\"zname\":\"\\u7231\\u6c99\\u5c3c\\u4e9a\"},{\"zcode\":373,\"zname\":\"\\u6469\\u5c14\\u591a\\u74e6\"},{\"zcode\":374,\"zname\":\"\\u4e9a\\u7f8e\\u5c3c\\u4e9a\"},{\"zcode\":375,\"zname\":\"\\u767d\\u4fc4\\u7f57\\u65af\"},{\"zcode\":376,\"zname\":\"\\u5b89\\u9053\\u5c14\\u5171\\u548c\\u56fd\"},{\"zcode\":377,\"zname\":\"\\u6469\\u7eb3\\u54e5\"},{\"zcode\":378,\"zname\":\"\\u5723\\u9a6c\\u529b\\u8bfa\"},{\"zcode\":380,\"zname\":\"\\u4e4c\\u514b\\u5170\"},{\"zcode\":381,\"zname\":\"\\u585e\\u5c14\\u7ef4\\u4e9a\\u5171\\u548c\\u56fd\"},{\"zcode\":382,\"zname\":\"\\u9ed1\\u5c71\\u5171\\u548c\\u56fd\"},{\"zcode\":385,\"zname\":\"\\u514b\\u7f57\\u5730\\u4e9a\"},{\"zcode\":386,\"zname\":\"\\u65af\\u6d1b\\u6587\\u5c3c\\u4e9a\"},{\"zcode\":387,\"zname\":\"\\u6ce2\\u65af\\u5c3c\\u4e9a\\u548c\\u9ed1\\u585e\\u54e5\\u7ef4\\u90a3\"},{\"zcode\":389,\"zname\":\"\\u9a6c\\u5176\\u987f\\u5171\\u548c\\u56fd\"},{\"zcode\":420,\"zname\":\"\\u6377\\u514b\\u5171\\u548c\\u56fd\"},{\"zcode\":421,\"zname\":\"\\u65af\\u6d1b\\u4f10\\u514b\"},{\"zcode\":423,\"zname\":\"\\u5217\\u652f\\u6566\\u58eb\\u767b\"},{\"zcode\":500,\"zname\":\"\\u798f\\u514b\\u5170\\u7fa4\\u5c9b\"},{\"zcode\":501,\"zname\":\"\\u4f2f\\u5229\\u5179\"},{\"zcode\":502,\"zname\":\"\\u5371\\u5730\\u9a6c\\u62c9\"},{\"zcode\":503,\"zname\":\"\\u8428\\u5c14\\u74e6\\u591a\"},{\"zcode\":504,\"zname\":\"\\u6d2a\\u90fd\\u62c9\\u65af\"},{\"zcode\":505,\"zname\":\"\\u5c3c\\u52a0\\u62c9\\u74dc\"},{\"zcode\":506,\"zname\":\"\\u54e5\\u65af\\u8fbe\\u9ece\\u52a0\"},{\"zcode\":507,\"zname\":\"\\u5df4\\u62ff\\u9a6c\"},{\"zcode\":509,\"zname\":\"\\u6d77\\u5730\"},{\"zcode\":590,\"zname\":\"\\u74dc\\u5fb7\\u7f57\\u666e\\u5c9b\"},{\"zcode\":591,\"zname\":\"\\u73bb\\u5229\\u7ef4\\u4e9a\"},{\"zcode\":592,\"zname\":\"\\u572d\\u4e9a\\u90a3\"},{\"zcode\":593,\"zname\":\"\\u5384\\u74dc\\u591a\\u5c14\"},{\"zcode\":594,\"zname\":\"\\u6cd5\\u5c5e\\u572d\\u4e9a\\u90a3\"},{\"zcode\":595,\"zname\":\"\\u5df4\\u62c9\\u572d\"},{\"zcode\":597,\"zname\":\"\\u82cf\\u91cc\\u5357\"},{\"zcode\":598,\"zname\":\"\\u4e4c\\u62c9\\u572d\"},{\"zcode\":599,\"zname\":\"\\u8377\\u5c5e\\u5b89\\u5fb7\\u70c8\\u65af\\u7fa4\\u5c9b\"},{\"zcode\":673,\"zname\":\"\\u6587\\u83b1\"},{\"zcode\":675,\"zname\":\"\\u5df4\\u5e03\\u4e9a\\u65b0\\u51e0\\u5185\\u4e9a\"},{\"zcode\":676,\"zname\":\"\\u6c64\\u52a0\"},{\"zcode\":678,\"zname\":\"\\u74e6\\u52aa\\u963f\\u56fe\"},{\"zcode\":679,\"zname\":\"\\u6590\\u6d4e\"},{\"zcode\":680,\"zname\":\"\\u5e15\\u52b3\"},{\"zcode\":682,\"zname\":\"\\u5e93\\u514b\\u7fa4\\u5c9b\"},{\"zcode\":687,\"zname\":\"\\u65b0\\u5580\\u91cc\\u591a\\u5c3c\\u4e9a\"},{\"zcode\":689,\"zname\":\"\\u6cd5\\u5c5e\\u6ce2\\u5229\\u5c3c\\u897f\\u4e9a\"},{\"zcode\":691,\"zname\":\"\\u5bc6\\u514b\\u7f57\\u5c3c\\u897f\\u4e9a\\u8054\\u90a6\"},{\"zcode\":852,\"zname\":\"\\u9999\\u6e2f\"},{\"zcode\":853,\"zname\":\"\\u6fb3\\u95e8\"},{\"zcode\":855,\"zname\":\"\\u67ec\\u57d4\\u5be8\"},{\"zcode\":856,\"zname\":\"\\u8001\\u631d\"},{\"zcode\":880,\"zname\":\"\\u5b5f\\u52a0\\u62c9\\u5171\\u548c\\u56fd\"},{\"zcode\":886,\"zname\":\"\\u53f0\\u6e7e\"},{\"zcode\":960,\"zname\":\"\\u9a6c\\u5c14\\u4ee3\\u592b\"},{\"zcode\":961,\"zname\":\"\\u9ece\\u5df4\\u5ae9\"},{\"zcode\":962,\"zname\":\"\\u7ea6\\u65e6\"},{\"zcode\":963,\"zname\":\"\\u53d9\\u5229\\u4e9a\"},{\"zcode\":964,\"zname\":\"\\u4f0a\\u62c9\\u514b\"},{\"zcode\":966,\"zname\":\"\\u6c99\\u7279\\u963f\\u62c9\\u4f2f\"},{\"zcode\":967,\"zname\":\"\\u4e5f\\u95e8\"},{\"zcode\":968,\"zname\":\"\\u963f\\u66fc\"},{\"zcode\":972,\"zname\":\"\\u4ee5\\u8272\\u5217\"},{\"zcode\":973,\"zname\":\"\\u5df4\\u6797\"},{\"zcode\":974,\"zname\":\"\\u5361\\u5854\\u5c14\"},{\"zcode\":975,\"zname\":\"\\u4e0d\\u4e39\"},{\"zcode\":976,\"zname\":\"\\u8499\\u53e4\"},{\"zcode\":977,\"zname\":\"\\u5c3c\\u6cca\\u5c14\"},{\"zcode\":992,\"zname\":\"\\u5854\\u5409\\u514b\\u65af\\u5766\"},{\"zcode\":993,\"zname\":\"\\u571f\\u5e93\\u66fc\\u65af\\u5766\"},{\"zcode\":994,\"zname\":\"\\u963f\\u585e\\u62dc\\u7586\\u5171\\u548c\\u56fd\"},{\"zcode\":995,\"zname\":\"\\u683c\\u9c81\\u5409\\u4e9a\"},{\"zcode\":996,\"zname\":\"\\u5409\\u5c14\\u5409\\u65af\"},{\"zcode\":998,\"zname\":\"\\u4e4c\\u5179\\u522b\\u514b\\u65af\\u5766\"},{\"zcode\":1242,\"zname\":\"\\u5df4\\u54c8\\u9a6c\"},{\"zcode\":1268,\"zname\":\"\\u5b89\\u63d0\\u74dc\\u548c\\u5df4\\u5e03\\u8fbe\"},{\"zcode\":1441,\"zname\":\"\\u767e\\u6155\\u5927\\u7fa4\\u5c9b\"},{\"zcode\":1671,\"zname\":\"\\u5173\\u5c9b\"},{\"zcode\":1787,\"zname\":\"\\u6ce2\\u591a\\u9ece\\u5404\"},{\"zcode\":1868,\"zname\":\"\\u7279\\u7acb\\u5c3c\\u8fbe\\u548c\\u591a\\u5df4\\u54e5\"},{\"zcode\":1876,\"zname\":\"\\u7259\\u4e70\\u52a0\"}]";

    public static final String ZCODE_SERVER_ENGLISH_TEXT = "[{\n" +
            "            \"zcode\": 86,\n" +
            "            \"zname\": \"China\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 1,\n" +
            "            \"zname\": \"United States\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 1,\n" +
            "            \"zname\": \"Canada\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 7,\n" +
            "            \"zname\": \"Kazakhstan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 7,\n" +
            "            \"zname\": \"Russia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 20,\n" +
            "            \"zname\": \"Egypt\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 27,\n" +
            "            \"zname\": \"South Africa\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 30,\n" +
            "            \"zname\": \"Greece\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 31,\n" +
            "            \"zname\": \"Netherlands\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 32,\n" +
            "            \"zname\": \"Belgium\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 33,\n" +
            "            \"zname\": \"France\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 34,\n" +
            "            \"zname\": \"Spain\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 36,\n" +
            "            \"zname\": \"Hungary\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 39,\n" +
            "            \"zname\": \"Italy\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 40,\n" +
            "            \"zname\": \"Romania\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 41,\n" +
            "            \"zname\": \"Switzerland\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 43,\n" +
            "            \"zname\": \"Austria\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 44,\n" +
            "            \"zname\": \"United Kingdom\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 45,\n" +
            "            \"zname\": \"Denmark\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 46,\n" +
            "            \"zname\": \"Sweden\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 47,\n" +
            "            \"zname\": \"Norway\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 48,\n" +
            "            \"zname\": \"Poland\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 49,\n" +
            "            \"zname\": \"Germany\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 51,\n" +
            "            \"zname\": \"Peru\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 52,\n" +
            "            \"zname\": \"Mexico\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 53,\n" +
            "            \"zname\": \"Cuba\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 54,\n" +
            "            \"zname\": \"Argentina\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 55,\n" +
            "            \"zname\": \"Brazil\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 56,\n" +
            "            \"zname\": \"Chile\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 57,\n" +
            "            \"zname\": \"Colombia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 58,\n" +
            "            \"zname\": \"Venezuela\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 60,\n" +
            "            \"zname\": \"Malaysia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 61,\n" +
            "            \"zname\": \"Australia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 62,\n" +
            "            \"zname\": \"Indonesia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 63,\n" +
            "            \"zname\": \"Philippines\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 64,\n" +
            "            \"zname\": \"New Zealand\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 65,\n" +
            "            \"zname\": \"Singapore\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 66,\n" +
            "            \"zname\": \"Thailand\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 81,\n" +
            "            \"zname\": \"Japan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 82,\n" +
            "            \"zname\": \"Korea\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 84,\n" +
            "            \"zname\": \"Vietnam\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 90,\n" +
            "            \"zname\": \"Turkey\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 91,\n" +
            "            \"zname\": \"India\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 92,\n" +
            "            \"zname\": \"Palestine\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 93,\n" +
            "            \"zname\": \"Afghanistan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 94,\n" +
            "            \"zname\": \"Sri Lanka\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 95,\n" +
            "            \"zname\": \"Myanmar\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 98,\n" +
            "            \"zname\": \"Iran\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 211,\n" +
            "            \"zname\": \"Sudan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 212,\n" +
            "            \"zname\": \"Morocco\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 213,\n" +
            "            \"zname\": \"Algeria\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 216,\n" +
            "            \"zname\": \"Tunisia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 218,\n" +
            "            \"zname\": \"Libya\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 220,\n" +
            "            \"zname\": \"Gambia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 221,\n" +
            "            \"zname\": \"Senegal\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 222,\n" +
            "            \"zname\": \"Mauritania\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 223,\n" +
            "            \"zname\": \"Mali\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 224,\n" +
            "            \"zname\": \"Papua New Guinea\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 226,\n" +
            "            \"zname\": \"Burkina Faso\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 227,\n" +
            "            \"zname\": \"Niger\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 228,\n" +
            "            \"zname\": \"Togo\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 229,\n" +
            "            \"zname\": \"Republic of Benin\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 230,\n" +
            "            \"zname\": \"Mauritius\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 231,\n" +
            "            \"zname\": \"Liberia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 232,\n" +
            "            \"zname\": \"Sierra Leone\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 233,\n" +
            "            \"zname\": \"Ghana\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 234,\n" +
            "            \"zname\": \"Nigeria\"\n" +
            "        },\n" +
            "       {\n" +
            "            \"zcode\": 235,\n" +
            "            \"zname\": “zade”\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 236,\n" +
            "            \"zname\": \"Central African Republic\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 237,\n" +
            "            \"zname\": \"Cameroon\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 238,\n" +
            "            \"zname\": \"Cape Verde\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 239,\n" +
            "            \"zname\": \"Sao Tome and Principe\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 240,\n" +
            "            \"zname\": \"Equatorial Guinea\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 241,\n" +
            "            \"zname\": \"Gabon\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 242,\n" +
            "            \"zname\": \"Congo\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 243,\n" +
            "            \"zname\": \"Democratic Republic of the Congo\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 244,\n" +
            "            \"zname\": \"Angola\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 245,\n" +
            "            \"zname\": \"Republic of Guinea-Bissau\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 248,\n" +
            "            \"zname\": \"Seychelles\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 250,\n" +
            "            \"zname\": \"Rwanda\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 251,\n" +
            "            \"zname\": \"Ethiopia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 252,\n" +
            "            \"zname\": \"Somalia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 253,\n" +
            "            \"zname\": \"Djibouti\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 254,\n" +
            "            \"zname\": \"Kenya\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 255,\n" +
            "            \"zname\": \"Tanzania\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 256,\n" +
            "            \"zname\": \"Uganda\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 257,\n" +
            "            \"zname\": \"Burundi\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 258,\n" +
            "            \"zname\": \"Mozambique\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 260,\n" +
            "            \"zname\": \"Zambia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 261,\n" +
            "            \"zname\": \"Madagascar\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 262,\n" +
            "            \"zname\": \"Reunion Island\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 263,\n" +
            "            \"zname\": \"Zimbabwe\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 264,\n" +
            "            \"zname\": \"Namibia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 265,\n" +
            "            \"zname\": \"Malawi\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 266,\n" +
            "            \"zname\": \"Lesotho\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 267,\n" +
            "            \"zname\": \"Botswana\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 268,\n" +
            "            \"zname\": \"Swaziland\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 291,\n" +
            "            \"zname\": \"Eritrea\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 297,\n" +
            "            \"zname\": \"Aruba\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 298,\n" +
            "            \"zname\": \"Faroe Islands\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 350,\n" +
            "            \"zname\": \"Gibraltar\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 351,\n" +
            "            \"zname\": \"Portugal\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 352,\n" +
            "            \"zname\": \"Luxembourg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 353,\n" +
            "            \"zname\": \"Ireland\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 354,\n" +
            "            \"zname\": \"Iceland\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 355,\n" +
            "            \"zname\": \"Albania\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 356,\n" +
            "            \"zname\": \"Malta\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 357,\n" +
            "            \"zname\": \"Cyprus\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 358,\n" +
            "            \"zname\": \"Finland\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 359,\n" +
            "            \"zname\": \"Bulgaria\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 370,\n" +
            "            \"zname\": \"Lithuania\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 371,\n" +
            "            \"zname\": \"Latvia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 372,\n" +
            "            \"zname\": \"Estonia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 373,\n" +
            "            \"zname\": \"Moldova\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 374,\n" +
            "            \"zname\": \"Armenia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 375,\n" +
            "            \"zname\": \"Belarus\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 376,\n" +
            "            \"zname\": \"Republic of Andorra\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 377,\n" +
            "            \"zname\": \"Monaco\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 378,\n" +
            "            \"zname\": \"San Marino\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 380,\n" +
            "            \"zname\": \"Ukraine\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 381,\n" +
            "            \"zname\": \"Republic of Serbia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 382,\n" +
            "            \"zname\": \"Montenegro Republic\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 385,\n" +
            "            \"zname\": \"Croatia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 386,\n" +
            "            \"zname\": \"Slovenia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 387,\n" +
            "            \"zname\": \"Bosnia and Herzegovina\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 389,\n" +
            "            \"zname\": \"Republic of Macedonia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 420,\n" +
            "            \"zname\": \"Czech Republic\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 421,\n" +
            "            \"zname\": \"Slovakia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 423,\n" +
            "            \"zname\": \"Liechtenstein\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 500,\n" +
            "            \"zname\": \"Falkland Islands\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 501,\n" +
            "            \"zname\": \"Belize\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 502,\n" +
            "            \"zname\": \"Guatemala\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 503,\n" +
            "            \"zname\": \"El Salvador\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 504,\n" +
            "            \"zname\": \"Honduras\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 505,\n" +
            "            \"zname\": \"Nicaragua\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 506,\n" +
            "            \"zname\": \"Costa Rica\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 507,\n" +
            "            \"zname\": \"Panama\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 509,\n" +
            "            \"zname\": \"Haiti\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 590,\n" +
            "            \"zname\": \"Guadeloupe\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 591,\n" +
            "            \"zname\": \"Bolivia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 592,\n" +
            "            \"zname\": \"Guyana\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 593,\n" +
            "            \"zname\": \"Ecuador\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 594,\n" +
            "            \"zname\": \"French Guiana\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 595,\n" +
            "            \"zname\": \"Paraguay\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 597,\n" +
            "            \"zname\": \"Suriname\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 598,\n" +
            "            \"zname\": \"Uruguay\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 599,\n" +
            "            \"zname\": \"Netherlands Andres\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 673,\n" +
            "            \"zname\": \"Brunei\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 675,\n" +
            "            \"zname\": \"Papua New Guinea\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 676,\n" +
            "            \"zname\": \"Tonga\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 678,\n" +
            "            \"zname\": \"Vanuatu\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 679,\n" +
            "            \"zname\": \"Fiji\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 680,\n" +
            "            \"zname\": \"Palau\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 682,\n" +
            "            \"zname\": \"Cook Islands\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 687,\n" +
            "            \"zname\": \"New Caledonia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 689,\n" +
            "            \"zname\": \"French Polynesia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 691,\n" +
            "            \"zname\": \"Federated States of Micronesia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 852,\n" +
            "            \"zname\": \"Hong Kong\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 853,\n" +
            "            \"zname\": \"Macau\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 855,\n" +
            "            \"zname\": \"Cambodia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 856,\n" +
            "            \"zname\": \"Laos\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 880,\n" +
            "            \"zname\": \"Bangladesh\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 886,\n" +
            "            \"zname\": \"Taiwan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 960,\n" +
            "            \"zname\": \"Maldives\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 961,\n" +
            "            \"zname\": \"Lebanon\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 962,\n" +
            "            \"zname\": \"Jordan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 963,\n" +
            "            \"zname\": \"Syria\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 964,\n" +
            "            \"zname\": \"Iraq\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 966,\n" +
            "            \"zname\": \"Saudi Arabia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 967,\n" +
            "            \"zname\": \"Yemen\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 968,\n" +
            "            \"zname\": \"Oman\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 972,\n" +
            "            \"zname\": \"Israel\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 973,\n" +
            "            \"zname\": \"Bahrain\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 974,\n" +
            "            \"zname\": \"Qatar\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 975,\n" +
            "            \"zname\": \"Bhutan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 976,\n" +
            "            \"zname\": \"Mongolia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 977,\n" +
            "            \"zname\": \"Nepal\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 992,\n" +
            "            \"zname\": \"Tajikistan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 993,\n" +
            "            \"zname\": \"Turkmenistan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 994,\n" +
            "            \"zname\": \"Republic of Azerbaijan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 995,\n" +
            "            \"zname\": \"Georgia\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 996,\n" +
            "            \"zname\": \"Kyrgyz\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 998,\n" +
            "            \"zname\": \"Uzbekistan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 1242,\n" +
            "            \"zname\": \"Bahamas\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 1268,\n" +
            "            \"zname\": \"Antigua and Barbuda\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 1441,\n" +
            "            \"zname\": \"Bermuda Islands\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 1671,\n" +
            "            \"zname\": \"Guam\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 1787,\n" +
            "            \"zname\": \"Puerto Rico\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 1868,\n" +
            "            \"zname\": \"Trinidad and Tobago\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"zcode\": 1876,\n" +
            "            \"zname\": \"Jamaica\"\n" +
            "        }]";
}
