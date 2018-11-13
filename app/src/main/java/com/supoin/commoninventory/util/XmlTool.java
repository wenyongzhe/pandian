package com.supoin.commoninventory.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

public class XmlTool {  
//    
//    public boolean writeXML(List<CallMessage> users){  
//        boolean flag=false;  
//        String str=writeToString(users);  
//          
//        flag=writeToXml(UserSessionDic.getContext(), str);  
//          
//        return flag;  
//    }  
//      
//     public String writeToString(List<CallMessage> users){  
//          //ʵ��xml��Ϣ���кŵ�һ������      
//        XmlSerializer serializer=Xml.newSerializer();        
//        StringWriter writer=new StringWriter();  
//        try{  
//            //xml���ݾ������л��󱣴浽String�У�Ȼ���ִ�ͨ��OutputStream����Ϊxml�ļ�             
//            serializer.setOutput(writer);                     
//            //�ĵ���ʼ          
//            serializer.startDocument("utf-8", true);                 
//            //��ʼһ���ڵ�             
//              
//            serializer.startTag("", "calls");  
//             serializer.attribute("", "type", "list");  
//               
//               
//               
//            for(CallMessage call :users){  
//                Log.i(Conf.TAG_DUBUG, "call="+JSON.toJSONString(call));  
//                 serializer.startTag("", "call");           
//                /** 
//                 * ������ 
//                 */  
////               serializer.attribute("", "ordernum", call.getOrdernum());  
//                 serializer.startTag("", "ordernum");    
//                 serializer.text(call.getOrdernum());  
//                 serializer.endTag("", "ordernum");      
//                   
//                 serializer.startTag("", "bytime");    
//                 serializer.text(call.getBytime());  
//                 serializer.endTag("", "bytime");      
//                   
//                 serializer.startTag("", "notifytime");    
//                 serializer.text(call.getNotifytime());  
//                 serializer.endTag("", "notifytime");      
//                   
//                 serializer.startTag("", "servertime");    
//                 serializer.text(call.getServertime());  
//                 serializer.endTag("", "servertime");      
//                   
//                   
//                 serializer.startTag("", "slocation");    
//                 serializer.text(call.getSlocation());  
//                 serializer.endTag("", "slocation");     
//                   
//                 serializer.startTag("", "isread");    
//                 serializer.text(call.getIsread());  
//                 serializer.endTag("", "isread");      
//                   
//                 serializer.endTag("", "call");           
//            }  
//             serializer.endTag("", "calls");       
//              
//             //�ر��ĵ�             
//             serializer.endDocument();  
//               
//        }catch (Exception e) {  
//            Log.i(Conf.TAG_ERROR, e.getMessage());  
//        }  
//        return writer.toString();  
//    }  
//      /** 
//       * ��xml�ַ���д��xml�ļ� 
//       * @param context 
//       * @param str 
//       * @return 
//       */  
//     public boolean writeToXml(Context context,String str){  
//            try {  
//                OutputStream out=context.openFileOutput("calls.xml", Context.MODE_PRIVATE);  
//                OutputStreamWriter outw=new OutputStreamWriter(out);  
//                try {  
//                    outw.write(str);  
//                    outw.close();  
//                    out.close();  
//                    return true;  
//                } catch (IOException e) {  
//                    // TODO Auto-generated catch block  
//                    return false;  
//                }  
//            } catch (FileNotFoundException e) {  
//                // TODO Auto-generated catch block  
//                return false;  
//            }  
//        }  
//       
//          
//        /** 
//         * ��֤�Ƿ���ڸ��ļ� 
//         * @return 
//         * @throws Exception 
//         */  
//        public boolean isExist()throws Exception{  
//            boolean flag=false;  
//            FileInputStream fs=  UserSessionDic.getContext().openFileInput("calls.xml");  
//            if(fs!=null){  
//                flag=true;  
//            }  
//              
//            return flag;  
//        }  
//       
//        public List<CallMessage> xmlParser() throws Exception{  
//              
//              
//             List<CallMessage> reslist= null;  
//             CallMessage call=null;  
//             try {  
//             FileInputStream fs=  UserSessionDic.getContext().openFileInput("calls.xml");  
//              
//              XmlPullParser parser = Xml.newPullParser();  
//                
//              parser.setInput(fs, "UTF-8");  
//              int eventType = parser.getEventType();  
//              while ((eventType = parser.next()) != XmlPullParser.END_DOCUMENT) {  
//                    
//                  switch (eventType) {  
//                    case XmlPullParser.START_TAG:  
//                        String name=parser.getName();  
//                        Log.i(Conf.TAG_DUBUG, "name��"+name);  
//                         if (parser.getName().equals("calls")) {  
//                            reslist = new ArrayList<CallMessage>();  
//                         } else if (name.equals("call")) {  
//                            call = new CallMessage(Conf.COMMANDID_RECEIVE);  
////                          contact.setId(Integer.valueOf(parser.getAttributeValue(0)));  
//                        } else if (name.equals("ordernum")) {  
//                            String ordernum=parser.nextText();  
//                            Log.i(Conf.TAG_DUBUG, "������"+ordernum);  
//                            call.setOrdernum(ordernum);  
//                        } else if (name.equals("bytime")) {  
//                            call.setBytime(parser.nextText());  
//                        }else if (name.equals("notifytime")) {  
//                            call.setNotifytime(parser.nextText());  
//                        }else if (name.equals("servertime")) {  
//                            call.setServertime(parser.nextText());  
//                        }else if (name.equals("slocation")) {  
//                            call.setSlocation(parser.nextText());  
//                        }else if (name.equals("isread")) {  
//                            call.setIsread(parser.nextText());  
//                        }  
//                           
//                           
//                        break;  
//  
//                    case XmlPullParser.END_TAG:  
//                        String name1=parser.getName();  
//                        if (name1.equals("call")) {  
//                            reslist.add(call);  
//                        }  
//                        break;  
//                    }  
//                    
//              }  
//                } catch (FileNotFoundException e) {  
//                    // TODO Auto-generated catch block  
//                    e.printStackTrace();  
//                } catch (IOException e) {  
//                    // TODO Auto-generated catch block  
//                    e.printStackTrace();  
//                } catch (Exception e) {  
//                    // TODO Auto-generated catch block  
//                    e.printStackTrace();  
//                }  
//             return reslist;  
//              
//        }  
}  