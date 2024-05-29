package sabin_rains;
import sabin_rains.*;

/*
 * StringPair类，用于存储两个字符串
 * 主要功能：
 * 1. 存储两个字符串
 * 2. 打印两个字符串
 * 3. 判断两个字符串是否相等
 * 4. 判断第一个字符串是否相等
 * 5. 判断第二个字符串是否相等
 * 6. 获取第一个字符串
 * 7. 获取第二个字符串
 */

public class StringPair {
        private String first;
        private String second;
    
        public StringPair(String first, String second) {
            this.first = first;
            this.second = second;
        }
        public void printPair(){
            System.out.println(this.first + " " + this.second);
        }

        public boolean matches(String a, String b) {
            return this.first.equals(a) && this.second.equals(b);
        }

        public boolean matches1st(String a) {
            return this.first.equals(a);
        }

        public boolean matches2nd(String b) {
            return this.second.equals(b);
        }

        public String getFirst() {
            return this.first;
        }

        public String getSecond() {
            return this.second;
        }
    
}
