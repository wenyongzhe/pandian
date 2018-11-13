    package com.supoin.commoninventory.util;  
      
    import java.util.Random;  
      
    /*根据给定的char集合，生成随机的字符串*/  
    public class StringWidthWeightRandom {  
        private Random widthRandom = new Random();  
        private int length;  
        private char[] chars={'0','1','2','3','4','5','6','7','8','9'};  
        private Random random = new Random();  
        public StringWidthWeightRandom(char[] chars) {  
            this.chars = chars;  
        }  
          
        //参数为生成的字符串的长度，根据给定的char集合生成字符串  
        public String getNextString(int length){      
              
            char[] data = new char[length];  
              
            for(int i = 0;i < length;i++){  
                int index = random.nextInt(chars.length);  
                data[i] = chars[index];  
            }  
            String s = new String(data);  
            return s;  
        }  
    }  