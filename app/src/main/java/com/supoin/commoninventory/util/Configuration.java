package com.supoin.commoninventory.util;

import java.nio.ReadOnlyBufferException;
import java.util.Hashtable;



public class Configuration{
        Hashtable list = new Hashtable();

//        private static ReadOnlyBufferException Configuration instance = new Configuration();
        private static Configuration instance = new Configuration();
        public static Configuration Instance(){
//            get
//            {
//                return instance;
//            }
        	return getInstance();
       }

        String filePath;
        
        public static Configuration getInstance() {
			return instance;
		}

		public static void setInstance(Configuration instance) {
			instance = instance;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

//		public String FilePath
//        {
//            get { return filePath; }
//            set { filePath = value; }
//        }

        private Configuration()
        {
            filePath = GetFilePath();
//            CheckUtil.SetFileAttribute(filePath);

            Load();
        }

        public void SetValue(String key, Object value)
        {
            // update internal list
//            list[key] = value;
            // update settings file
            Save();
        }

        /// <summary>
        /// Return specified settings as String.
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        public String GetString(String key)
        {
            Object result = null;
            result = list.get(key);
            if(result!=null)
            	return result.toString();
            return null;
        }

        /// <summary>
        /// Return specified settings as integer.
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        public int GetInt(String key)
        {
            String result = GetString(key);
            return (result.equals(null)) ? 0 : Integer.parseInt(result);
        }

        /// <summary>
        /// Return specified settings as boolean.
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        public Boolean GetBool(String key)
        {
            String result = GetString(key);
            return (result.equals(null)) ? false : Boolean.valueOf(result);
        }

        public void Load()
        {
//            XmlTextReader reader = null;
//            try
//            {
//                list.Clear();
//
//                reader = new XmlTextReader(filePath);
//                while (reader.Read())
//                {
//                    if ((reader.NodeType == XmlNodeType.Element) && (reader.Name == "add"))
//                        list[reader.GetAttribute("key")] = reader.GetAttribute("value");
//                }
//            }
//            catch (Exception ex)
//            {
//            }
//            finally
//            {
//                if (reader.ReadState != ReadState.Closed)
//                {
//                    reader.Close();
//                }
//            }
        }

        public void Save()
        {
//                XmlDocument doc = new XmlDocument();
//                Xml
//                doc.Load(filePath);
//
//                XmlNodeList nodeList = doc.SelectNodes("/configuration/appSettings/add");
//                foreach (XmlNode node : nodeList)
//                {
//                    node.Attributes["value"].Value = GetString(node.Attributes["key"].Value);
//                }
//
//                doc.Save(filePath);

        }

        private String GetFilePath()
        {
         //   return Assembly.GetExecutingAssembly().GetName().CodeBase + ".config";
        	return null;
        }
    }

