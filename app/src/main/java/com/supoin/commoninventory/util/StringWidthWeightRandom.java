    package com.supoin.commoninventory.util;  
      
    import java.util.Random;  
      
    /*���ݸ�����char���ϣ�����������ַ���*/  
    public class StringWidthWeightRandom {  
        private Random widthRandom = new Random();  
        private int length;  
        private char[] chars={'0','1','2','3','4','5','6','7','8','9'};  
        private Random random = new Random();  
        public StringWidthWeightRandom(char[] chars) {  
            this.chars = chars;  
        }  
          
        //����Ϊ���ɵ��ַ����ĳ��ȣ����ݸ�����char���������ַ���  
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