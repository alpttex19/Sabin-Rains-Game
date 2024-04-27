package chess_board;

public class StringPair {
        private String first;
        private String second;
    
        public StringPair(String first, String second) {
            this.first = first;
            this.second = second;
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
    
}
