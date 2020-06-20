package com.anyu.library.utils;

public interface LibraryConstant {
    //默认状态登录凭证的状态时间
    int DEFAULT_EXPIRED_SECOND = 3600 * 12 * 30;

    enum BookType{
        ALL("全部",1), LITERATURE("文学",2),
        HISTORY("历史",3 ),SUSPENSE("悬疑",4),
        HEALTH("健康",5);

        private final String type;
        private final int index;
        BookType(String type, int index) {
            this.type  = type;
            this.index = index;
        }

        public String Type() {
            return this.type;
        }

        public int Index() {
            return this.index;
        }
    }
}
