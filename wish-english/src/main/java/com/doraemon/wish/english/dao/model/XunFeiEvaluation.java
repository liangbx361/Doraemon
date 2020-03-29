package com.doraemon.wish.english.dao.model;

import java.util.List;

public class XunFeiEvaluation {

    /**
     * data : {"read_word":{"lan":"en","type":"study","version":"6.5.0.1011","rec_paper":{"read_word":{"except_info":"28680","is_rejected":"false","total_score":"64.725080","sentence":[{"beg_pos":"0","content":"apple","end_pos":"129","word":{"beg_pos":"79","content":"apple","end_pos":"129","total_score":"94.963020"}},{"beg_pos":"129","content":"banana","end_pos":"163","word":{"beg_pos":"163","content":"banana","end_pos":"163","total_score":"0.000000"}},{"beg_pos":"163","content":"orange","end_pos":"226","word":{"beg_pos":"163","content":"orange","end_pos":"226","total_score":"99.212200"}},{"content":"banana","end_pos":"359","word":{"beg_pos":"265","content":"banana","end_pos":"318"},"beg_pos":"226"}],"beg_pos":"0","content":"apple banana orange","end_pos":"359"}}}}
     * code : 0
     * desc : success
     * sid : wse00000001@ll36940e324c59000100
     */

    public DataBean data;
    public String code;
    public String desc;
    public String sid;

    public static class DataBean {
        /**
         * read_word : {"lan":"en","type":"study","version":"6.5.0.1011","rec_paper":{"read_word":{"except_info":"28680","is_rejected":"false","total_score":"64.725080","sentence":[{"beg_pos":"0","content":"apple","end_pos":"129","word":{"beg_pos":"79","content":"apple","end_pos":"129","total_score":"94.963020"}},{"beg_pos":"129","content":"banana","end_pos":"163","word":{"beg_pos":"163","content":"banana","end_pos":"163","total_score":"0.000000"}},{"beg_pos":"163","content":"orange","end_pos":"226","word":{"beg_pos":"163","content":"orange","end_pos":"226","total_score":"99.212200"}},{"content":"banana","end_pos":"359","word":{"beg_pos":"265","content":"banana","end_pos":"318"},"beg_pos":"226"}],"beg_pos":"0","content":"apple banana orange","end_pos":"359"}}}
         */

        public ReadWordBeanX read_word;

        public static class ReadWordBeanX {
            /**
             * lan : en
             * type : study
             * version : 6.5.0.1011
             * rec_paper : {"read_word":{"except_info":"28680","is_rejected":"false","total_score":"64.725080","sentence":[{"beg_pos":"0","content":"apple","end_pos":"129","word":{"beg_pos":"79","content":"apple","end_pos":"129","total_score":"94.963020"}},{"beg_pos":"129","content":"banana","end_pos":"163","word":{"beg_pos":"163","content":"banana","end_pos":"163","total_score":"0.000000"}},{"beg_pos":"163","content":"orange","end_pos":"226","word":{"beg_pos":"163","content":"orange","end_pos":"226","total_score":"99.212200"}},{"content":"banana","end_pos":"359","word":{"beg_pos":"265","content":"banana","end_pos":"318"},"beg_pos":"226"}],"beg_pos":"0","content":"apple banana orange","end_pos":"359"}}
             */

            public String lan;
            public String type;
            public String version;
            public RecPaperBean rec_paper;

            public static class RecPaperBean {
                /**
                 * read_word : {"except_info":"28680","is_rejected":"false","total_score":"64.725080","sentence":[{"beg_pos":"0","content":"apple","end_pos":"129","word":{"beg_pos":"79","content":"apple","end_pos":"129","total_score":"94.963020"}},{"beg_pos":"129","content":"banana","end_pos":"163","word":{"beg_pos":"163","content":"banana","end_pos":"163","total_score":"0.000000"}},{"beg_pos":"163","content":"orange","end_pos":"226","word":{"beg_pos":"163","content":"orange","end_pos":"226","total_score":"99.212200"}},{"content":"banana","end_pos":"359","word":{"beg_pos":"265","content":"banana","end_pos":"318"},"beg_pos":"226"}],"beg_pos":"0","content":"apple banana orange","end_pos":"359"}
                 */

                public ReadWordBean read_word;

                public static class ReadWordBean {
                    /**
                     * except_info : 28680
                     * is_rejected : false
                     * total_score : 64.725080
                     * sentence : [{"beg_pos":"0","content":"apple","end_pos":"129","word":{"beg_pos":"79","content":"apple","end_pos":"129","total_score":"94.963020"}},{"beg_pos":"129","content":"banana","end_pos":"163","word":{"beg_pos":"163","content":"banana","end_pos":"163","total_score":"0.000000"}},{"beg_pos":"163","content":"orange","end_pos":"226","word":{"beg_pos":"163","content":"orange","end_pos":"226","total_score":"99.212200"}},{"content":"banana","end_pos":"359","word":{"beg_pos":"265","content":"banana","end_pos":"318"},"beg_pos":"226"}]
                     * beg_pos : 0
                     * content : apple banana orange
                     * end_pos : 359
                     */

                    public String except_info;
                    public String is_rejected;
                    public int total_score;
                    public String beg_pos;
                    public String content;
                    public String end_pos;
                    public List<SentenceBean> sentence;

                    public static class SentenceBean {
                        /**
                         * beg_pos : 0
                         * content : apple
                         * end_pos : 129
                         * word : {"beg_pos":"79","content":"apple","end_pos":"129","total_score":"94.963020"}
                         */

                        public String beg_pos;
                        public String content;
                        public String end_pos;
                        public WordBean word;

                        public static class WordBean {
                            /**
                             * beg_pos : 79
                             * content : apple
                             * end_pos : 129
                             * total_score : 94.963020
                             */

                            public String beg_pos;
                            public String content;
                            public String end_pos;
                            public String total_score;
                        }
                    }
                }
            }
        }
    }
}
