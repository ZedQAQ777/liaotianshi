package socket.ws;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import socket.controller.ApiQunController;
import org.springframework.beans.factory.annotation.Autowired;
import socket.utils.RedisUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@ServerEndpoint(value = "/ws/{userId}")
public class WsEndpoint {


	@Autowired
	ApiQunController qunWS;
	
    //ConcurrentHashMap是线程安全的，而HashMap是线程不安全的。
    private static ConcurrentHashMap<String,Session> mapUS = new ConcurrentHashMap<String,Session>();  
    private static ConcurrentHashMap<Session,String> mapSU = new ConcurrentHashMap<Session,String>();

    //连接建立成功调用的方法
	@OnOpen
	public void onOpen(Session session,@PathParam("userId") Integer userId) {
		String jsonString="{'content':'online','id':"+userId+",'type':'onlineStatus'}";
		for (String value : mapSU.values()) {
			try {
				mapUS.get(value).getBasicRemote().sendText(jsonString);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		mapUS.put(userId+"",session);
		mapSU.put(session,userId+"");
		//更新redis中的用户在线状态
		RedisUtils.set(userId+"_status","online");

	}
  
    //连接关闭调用的方法 
    @OnClose  
    public void onClose(Session session) { 
    	String userId=mapSU.get(session);
    	if(userId!=null&&userId!=""){
    	 	//更新redis中的用户在线状态
    		RedisUtils.set(userId+"_status","offline");
        	mapUS.remove(userId);
        	mapSU.remove(session);
    	}
    }  
  
    // 收到客户端消息后调用的方法 
    @OnMessage  
    public void onMessage(String message, Session session) {  
    	   JSONObject jsonObject=JSONObject.fromObject(message);
           String type = jsonObject.getJSONObject("to").getString("type");
           if(type.equals("onlineStatus")){
				for(Session s:session.getOpenSessions()){		//循环发给所有在线的人
				   JSONObject toMessage=new JSONObject();
	               toMessage.put("id", jsonObject.getJSONObject("mine").getString("id"));  
	               toMessage.put("content", jsonObject.getJSONObject("mine").getString("content"));
	               toMessage.put("type",type);
					for (String value : mapSU.values()) {
						try {
							mapUS.get(value).getBasicRemote().sendText(toMessage.toString());
						} catch (IOException e) {
						}
					}
				}
           }else{
               int toId=jsonObject.getJSONObject("to").getInt("id");
               SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");    
               Date date = new Date();
               String time=df.format(date);    
               jsonObject.put("time", time); 
               JSONObject toMessage=new JSONObject();
               toMessage.put("avatar", jsonObject.getJSONObject("mine").getString("avatar"));  
               toMessage.put("type",type);      
               toMessage.put("content", jsonObject.getJSONObject("mine").getString("content"));   
               toMessage.put("timestamp",date.getTime()); 
               toMessage.put("time",time); 
               toMessage.put("mine",false);
               toMessage.put("username",jsonObject.getJSONObject("mine").getString("username"));   
    	   	    if(type.equals("friend")||type.equals("fankui")){
    	   	    	   toMessage.put("id", jsonObject.getJSONObject("mine").getInt("id"));    
    	   	    }else{
    	   	    	   toMessage.put("id", jsonObject.getJSONObject("to").getInt("id"));   
    	   	    }        
               switch (type) {
    		   		case "friend":           							 //单聊,记录到mongo
    			    	  if(mapUS.containsKey(toId+"")){               //如果在线，及时推送
    			  			 mapUS.get(toId+"").getAsyncRemote().sendText(toMessage.toString());               //发送消息给对方
    			    	  }else{                                        //如果不在线 就记录到数据库，下次对方上线时推送给对方。
    			    		RedisUtils.lpush(toId + "_msg", toMessage.toString());
    			    	  }
    		   			break;
    		   		case "group":
    		   				JSONArray memberList=JSONArray.fromObject(qunWS.getSimpleMemberByGroupId(toId));  //获取群成员userId列表
    		   				if(memberList.size()>0){              
    		   					for(int i=0;i<memberList.size();i++){                            //发送到在线用户(除了发送者)
    		   						if(mapUS.containsKey(memberList.get(i)) && !memberList.get(i).equals(jsonObject.getJSONObject("mine").getInt("id")+"")){
    		   						  session=mapUS.get(memberList.get(i));
    								  session.getAsyncRemote().sendText(toMessage.toString());
    		   						}else if(memberList.get(i).equals(jsonObject.getJSONObject("mine").getInt("id")+"")){
    		   							      	 //如果是发送者自己，不做任何操作。
    		   						}else{    	//如果是离线用户,数据存到redis待用户上线后推送。
    		   						   RedisUtils.lpush(memberList.get(i) + "_msg", toMessage.toString());
    		   						}
    		   					}
    		   				}
    		   			break;
    		   		default:
    		   			break;
       		 }       
           }

    }  
  
    /** 
     * 发生错误时调用 
     * @param session 
     * @param error 
     */  
    @OnError  
    public void onError(Session session, Throwable error) {  
    	String userId=mapSU.get(session);
    	if(userId!=null&&userId!=""){
    	 	//更新redis中的用户在线状态
    		RedisUtils.set(userId+"_status","offline");
        	mapUS.remove(userId);
        	mapSU.remove(session);
    	}
        error.printStackTrace();
    }

    /** 
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。 
     */  
    public void sendMessage(Session session,String message) {  
           session.getAsyncRemote().sendText(message);  
    }  
	
}
