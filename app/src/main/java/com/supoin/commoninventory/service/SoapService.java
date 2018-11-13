package com.supoin.commoninventory.service;


public abstract class SoapService implements ISoapService{

//	/*和WCF ServiceContract中的Namespace一致*/  
//    private static final String NAMESPACE="http://tempuri.org/";
//    /*WCF在iis中的调用路径(http://服务器/虚拟目录/服务)*/  
//    //private static String URL="http://localhost:8010/PDAService";  
//    /*Namespace/服务接口/方法*/  
//    private static final String SOAPACTION="http://tempuri.org/IDrugCodeDBService/";
//    
//	@Override
//	//添加品种码信息
//	public 	SoapObject SaveProductInfo (String inputjson){
//		// TODO Auto-generated method stub
//		String methodName = "SaveProductInfo";	
//        SoapObject soapObject=new SoapObject(NAMESPACE, methodName);  
//        soapObject.addProperty("pdaNo", pdaNo);//传参，记住参数名必须和WCF方法中的参数名一致
//        soapObject.addProperty("branchCN", branchCN);
//        soapObject.addProperty("routeNo", routeNo);
//        return accessWcf(soapObject, methodName);
//	}
//		
////		//删除品种码信息
////		SoapObject delProductInfo (String inputjson);
////		
////		//保存 品种码与商品码关联关系
////		SoapObject SaveDrugRelation (String inputjson);
////		
////		//删除品种码与商品码关联关系
////		SoapObject delDrugRelation (String inputjson);
////		
////		//获取数据库文件信息
////		SoapObject  GetDBFileSize();//DownFileInfo
////		
////		//获取对比表记录行数
////		SoapObject getRelationInfor (String inputjson);
//	public SoapObject T_CheckDownTask(String pdaNo, String branchCN,
//			String routeNo) {
//		// TODO Auto-generated method stub
//		String methodName = "T_CheckDownTask";
//		
//        SoapObject soapObject=new SoapObject(NAMESPACE, methodName);  
//        soapObject.addProperty("pdaNo", pdaNo);//传参，记住参数名必须和WCF方法中的参数名一致
//        soapObject.addProperty("branchCN", branchCN);
//        soapObject.addProperty("routeNo", routeNo);
//        return accessWcf(soapObject, methodName);
//	}
//	@Override
//	public SoapObject T_DownAddTask(String pdaNo, String branchCN) {
//		// TODO Auto-generated method stub
//		String methodName = "T_DownAddTask";
//        SoapObject soapObject=new SoapObject(NAMESPACE, methodName);  
//        soapObject.addProperty("pdaNo", pdaNo);
//        soapObject.addProperty("branchCN", branchCN);
//        return accessWcf(soapObject, methodName);
//	}
//	@Override
//	public SoapObject T_DownDeleteTask(String pdaNo, String branchCN) {
//		// TODO Auto-generated method stub
//		String methodName = "T_DownDeleteTask";
//        SoapObject soapObject=new SoapObject(NAMESPACE, methodName);  
//        soapObject.addProperty("pdaNo", pdaNo);
//        soapObject.addProperty("branchCN", branchCN);
//        return accessWcf(soapObject, methodName);
//	}
//	@Override
//	public SoapObject T_DownChangeStaff(String pdaNo, String branchCN) {
//		// TODO Auto-generated method stub
//		String methodName = "T_DownChangeStaff";
//        SoapObject soapObject=new SoapObject(NAMESPACE, methodName);  
//        soapObject.addProperty("pdaNo", pdaNo);
//        soapObject.addProperty("branchCN", branchCN);
//        return accessWcf(soapObject, methodName);
//	}
//	@Override
//	public SoapObject T_DownRoute(String pdaNo, String branchCN, String routeNo) {
//		// TODO Auto-generated method stub
//		String methodName = "T_DownRoute";
//        SoapObject soapObject=new SoapObject(NAMESPACE, methodName);  
//        soapObject.addProperty("pdaNo", pdaNo);
//        soapObject.addProperty("branchCN", branchCN);
//        soapObject.addProperty("routeNo", routeNo);
//        return accessWcf(soapObject, methodName);
//	}
//	
//	@Override
//	public SoapObject PreReach(String pdaNo, String branchCN, int taskID,
//			int midNodeSN, String currentNode, String nextNode) {
//		// TODO Auto-generated method stub
//		String methodName = "PreReach";
//        SoapObject soapObject=new SoapObject(NAMESPACE, methodName);  
//        soapObject.addProperty("pdaNo", pdaNo);
//        soapObject.addProperty("branchCN", branchCN);
//        soapObject.addProperty("taskID", taskID);
//        soapObject.addProperty("midNodeSN", midNodeSN);
//        soapObject.addProperty("currentNode", currentNode);
//        soapObject.addProperty("nextNode", nextNode);
//        
//        return accessWcf(soapObject, methodName);
//	}
//	@Override
//	public SoapObject Doorbell(String pdaNo, String branchCN, int taskID,
//			int midNodeSN, String nodeNo) {
//		// TODO Auto-generated method stub
//		String methodName = "Doorbell";
//        SoapObject soapObject=new SoapObject(NAMESPACE, methodName);  
//        soapObject.addProperty("pdaNo", pdaNo);
//        soapObject.addProperty("branchCN", branchCN);
//        soapObject.addProperty("taskID", taskID);
//        soapObject.addProperty("midNodeSN", midNodeSN);
//        soapObject.addProperty("nodeNo", nodeNo);
//        return accessWcf(soapObject, methodName);
//	}
//	@Override
//	public SoapObject GetPreSendBox(String pdaNo, String branchCN) {
//		// TODO Auto-generated method stub
//		String methodName = "GetPreSendBox";
//        SoapObject soapObject=new SoapObject(NAMESPACE, methodName);  
//        soapObject.addProperty("pdaNo", pdaNo);
//        soapObject.addProperty("branchCN", branchCN);
//        return accessWcf(soapObject, methodName);
//	}
//	@Override
//	public SoapObject PdaToNodeNotice(String pdaNo, String branchCN,
//			int taskID, int midNodeSN, String nodeNo, String noticeContent) {
//		// TODO Auto-generated method stub
//		String methodName = "PdaToNodeNotice";
//        SoapObject soapObject=new SoapObject(NAMESPACE, methodName);  
//        soapObject.addProperty("pdaNo", pdaNo);
//        soapObject.addProperty("branchCN", branchCN);
//        
//        soapObject.addProperty("taskID", taskID);
//        soapObject.addProperty("midNodeSN", midNodeSN);
//        soapObject.addProperty("nodeNo", nodeNo);
//        soapObject.addProperty("noticeContent", noticeContent);
//        return accessWcf(soapObject, methodName);
//	}
//	@Override
//	public SoapObject PdaDeleteDownRoute(String pdaNo, String branchCN,
//			String routeNo) {
//		// TODO Auto-generated method stub
//		String methodName = "PdaDeleteDownRoute";
//        SoapObject soapObject=new SoapObject(NAMESPACE, methodName);  
//        soapObject.addProperty("pdaNo", pdaNo);
//        soapObject.addProperty("branchCN", branchCN);
//        soapObject.addProperty("routeNo", routeNo);
//        return accessWcf(soapObject, methodName);
//	}
//	@Override
//	public SoapObject PdaDeleteDownNewTask(String pdaNo, String branchCN,
//			int taskID) {
//		// TODO Auto-generated method stub
//		String methodName = "PdaDeleteDownNewTask";
//        SoapObject soapObject=new SoapObject(NAMESPACE, methodName);  
//        soapObject.addProperty("pdaNo", pdaNo);
//        soapObject.addProperty("branchCN", branchCN);
//        soapObject.addProperty("taskID", taskID);
//        return accessWcf(soapObject, methodName);
//	}
//	
//	@Override
//	public SoapObject T_UpTaskOver(String pdaNo, String branchCN, String upXml,
//			int pType) {
//		// TODO Auto-generated method stub
//		String methodName = "T_UpTaskOver";
//        SoapObject soapObject=new SoapObject(NAMESPACE, methodName);  
//        soapObject.addProperty("pdaNo", pdaNo);
//        soapObject.addProperty("branchCN", branchCN);
//        soapObject.addProperty("upXml", upXml);
//        soapObject.addProperty("pType", pType);
//        
//        return accessWcf(soapObject, methodName);
//	}
//
//	private SoapObject accessWcf(SoapObject soapObject, String methodName)
//	{
//		SoapObject result=null;
//	    SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);  
//	 
//        //envelope.addMapping(ChangeStaffEntity.NAMESPACE, "ChangeStaffEntity", ChangeStaffEntity.class);
//        envelope.setOutputSoapObject(soapObject);  
//        envelope.bodyOut=soapObject;  
//        envelope.dotNet=true;
//        
//        HttpTransportSE transportSE=new HttpTransportSE(StaticPublicParams.URL);  
//        transportSE.debug=true;//使用调式功能  
//        try {  
//            transportSE.call(SOAPACTION + methodName, envelope);  
//            result=(SoapObject) envelope.bodyIn;
//            
//        } catch (Exception e) {  
//            return null; 
//        }  
//        return result;
//	}
}
