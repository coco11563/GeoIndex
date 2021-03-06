package method;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import json.JSONArray;
import json.JSONException;
import json.JSONObject;
public class readJSONFile {
	
	/**
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public static JSONArray read_jsonFile ( String path ) throws IOException,JSONException
	{
		JSONArray json_array = new JSONArray ( ) ;
		File file = new File ( path ) ; 
		BufferedReader reader = new BufferedReader ( new FileReader ( file ) ) ;
		String tempString = null ;
		while ( ( tempString = reader.readLine ( ) ) != null )
		{
			json_array.put ( new JSONObject ( tempString ) ) ;
		} 
		reader.close( );
		return json_array;
	}
	
	/**
	 * 将读取到的json文件重新存储到write_path路径下。命名为武汉市.json
	 * 
	 * @param array
	 * @throws JSONException
	 * @throws Exception
	 */
	public static void storFiles(JSONArray array) throws JSONException, Exception
	{
		String 				write_path				=		"" ;
		JSONObject 		write_json_object 	= 		null ;
		File 					write_file				=		null ;
		FileWriter 			write_fw					=		null ;
		int count = 0;
		for(int i=0;i<array.length();i++)
		{
			write_json_object 	= 	array.getJSONObject ( i );
			write_path 				=  	"D:\\sinadata" ;		//存储路径
			//write_path 				=  	"C:\\Users\\xgxy003win8\\Pictures" ;
			write_file 				= 		new File ( write_path );
			if ( !write_file.isFile ( ) ) write_file.mkdirs  ( );
			write_fw 				= 		new FileWriter(write_path + "\\" + "武汉市.json",true);
			write_fw.write( ( write_json_object.toString ( ) + "\r\n") );
			count++;
			System.out.println(count);
			write_fw.close();
			write_fw = null;
		}
	}
	public static void storlistFiles(List<String> array) throws JSONException, Exception
	{
		String 				write_path				=		"" ;
		File 					write_file				=		null ;
		FileWriter 			write_fw					=		null ;
		int count=0;
		for(int i=0;i<array.size();i++)
		{
			String write_json_object 	= 	array.get(i);
			write_path 				=  	"E:\\data" ;		//存储路径
			//write_path 				=  	"C:\\Users\\xgxy003win8\\Pictures" ;
			write_file 				= 		new File ( write_path );
			if ( !write_file.isFile ( ) ) write_file.mkdirs  ( );
			write_fw 				= 		new FileWriter(write_path + "\\" + "华科签到.json",true);
			write_fw.write( ( write_json_object.toString ( ) + "\r\n") );
			count++;
			System.out.println(count);
			write_fw.close();
			write_fw = null;
		}
	}
	
	
	/**
	 *  循环读取某路径下所有武汉市的数据，并汇总。
	 * @param file
	 * @throws Exception
	 */
	
	public static void readFile(File file) throws Exception
	{
		
		if(file!=null)
		{
			if(file.isDirectory())
			{
				File f[]= file.listFiles();
				if(f!=null)
				{
					for(int i=0;i<f.length;i++)
					{
						readFile(f[i]);
					}
				}
			}else
			{
				if(file.getName().equals("武汉市.json"))
				{
					System.out.println(file);
					JSONArray json_array = readJSONFile.read_jsonFile (file.toString());
					storFiles(json_array);
				}	
			}
		}
	}
	


	/**
	 * 去除poiid中重复的id
	 * @param list
	 * @return
	 */
	public static List<String> dupliDel(List<String> list){
		int count =0;
		List<String> templist = new ArrayList<String>();
		Iterator<String> it=list.iterator(); 
		while(it.hasNext()){  
			String a=it.next();  
			if(templist.contains(a)){  
				it.remove();  
				count ++;
			}else{  
				templist.add(a);  
			}  
		}
		System.out.println("共有"+count+"条重复记录；"); 
		return templist;
	}
	
	
	/**
	 *  循环读取某路径下所有武汉市的签到数据，并提取id  picurl
	 * @param file
	 * @throws Exception
	 */
	static List<List<String>> id_pic_list =new ArrayList<List<String>>();
	static int pic_sum = 0;
	static List<String> data_array =new ArrayList<String>();
	public static List<List<String>> readFilePic(File file) throws Exception{
		//public static List<String> readFilePic(File file) throws Exception{	
		if(file!=null)
		{
			if(file.isDirectory())
			{
				File f[]= file.listFiles();
				if(f!=null)
				{
					for(int i=0;i<f.length;i++)
					{
						readFilePic(f[i]);
					}
				}
			}else
			{
				if(file.getName().equals("武汉市.json"))
				{
					System.out.println(file);
					JSONArray json_array = readJSONFile.read_jsonFile (file.toString());
					//提取签到数据中的坐标和
					for(int i = 0;i<json_array.length();i++){
						JSONObject data = json_array.getJSONObject(i);
						String geo = data.getString ( "geo" );
						JSONObject geoObject = new JSONObject ( geo );
						String coordinates = geoObject.getString ( "coordinates" );
						String locationString[] = coordinates.split ( "," );
						Double lat = Double.valueOf ( locationString [ 0 ].substring ( 1,
								locationString [ 0 ].length ( ) ) );
						Double lon= Double.valueOf ( locationString [ 1 ].substring ( 0,
								locationString [ 1 ].length ( ) - 1 ) );
						
							if(json_array.getJSONObject(i).has("annotations")){
							if(json_array.getJSONObject(i).getJSONArray("annotations").getJSONObject(0).has("place")){	
								pic_sum ++;
								List<String> id_pic= new ArrayList<String>();
								String id =String.valueOf(lon);
								String pic_url =String.valueOf(lat);
								id_pic.add(id);
								id_pic.add(pic_url);
								if(json_array.getJSONObject(i).getJSONArray("annotations").getJSONObject(0).getJSONObject("place").has("title")){
									String poiname = json_array.getJSONObject(i).getJSONArray("annotations").getJSONObject(0).getJSONObject("place").getString("title");
									id_pic.add(poiname);
									
								}
								String text = json_array.getJSONObject(i).getString("text");
								id_pic.add(text);
								id_pic_list.add(id_pic);	
							}
							}
							
					}
				}	
			}
		}
		return id_pic_list;
		//return data_array;
	}
	
	private static String readFile(String filePath) {
		String data = "";
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					data += lineTxt;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return data;
	}
	static private double wkt_getZoomRes ( int zoom )
	{
		double tileSize = 256; // 瓦片尺寸(256*256)
		double initialResolution = 2 * Math.PI * 6378137 / tileSize; // 6378137为球体半径
		double res = initialResolution / Math.pow ( 2, zoom ); // zoom为层数(0-21)
		return res;
	}

	static private double [ ] wkt_lonLat2Mercator ( double lon, double lat )
	{
		double [ ] xy = new double [ 2 ];
		double x = lon * 20037508.342789 / 180;
		double y = Math.log ( Math.tan ( ( 90 + lat ) * Math.PI / 360 ) )
				/ ( Math.PI / 180 );
		y = y * 20037508.34789 / 180;
		xy [ 0 ] = x;
		xy [ 1 ] = y;
		return xy;
	}
	static public String wkt_code ( double lon, double lat, int zoom )
	{
		// lon：经
		// lat：纬
		double web_mkt[] = wkt_lonLat2Mercator ( lon, lat );
		double x = web_mkt [ 0 ] + 20037508.342789;
		double y = 20037508.342789 - web_mkt [ 1 ];
		double res = wkt_getZoomRes ( zoom );
		double spanX = res * 256;// zu[0]为1px代表的纬度
		double spanY = res * 256;// zu[1]为1px代表的经度
		int bx = (int) ( x / spanX );
		int by = (int) ( y / spanY );
		return zoom + "_" + bx + "_" + by;
	}
	public static void main(String args[]) throws Exception{
		//获取签到数据中的id、图片url数据。
		
//		String write_path = "";
//		File 					write_file				=		null ;
//		FileWriter 			write_fw					=		null ;
//		String checkfileName="E:\\data";	//读取制定文件夹下的所有武汉市.json 文件
//		File file1 = new File(checkfileName);
//		List<List<String>> pic_list = readFilePic(file1);
//		System.out.println(pic_sum);
//		for(int i =0 ;i<pic_list.size();i++){
//			String id = pic_list.get(i).get(0);
//			String pic_url = pic_list.get(i).get(1);
//			String poiname = pic_list.get(i).get(2);
//			String text = pic_list.get(i).get(3);
//			write_path 					=  	"E:\\data" ;		//存储路径
//			write_file 					= 		new File ( write_path );
//			if ( !write_file.isFile ( ) ) write_file.mkdirs  ( );
//			//write_fw 				= 		new FileWriter(write_path + "/" + "id_picurl.txt",true);
//			write_fw 				= 		new FileWriter(write_path + "/" + "华科坐标.txt",true);
//			write_fw.write(id + " " +  pic_url +" "+poiname+" "+text + "\r\n");
//			write_fw.close();
//			write_fw = null;
	
//		}
		/*
		String list = readFile("C:\\Users\\coco1\\workspace\\GeoIndex\\data\\pointType.json");
		JSONArray listjson = new JSONArray(list);
		int index = 0;
		for(int i = 0 ; i < listjson.length() ; i ++)
		{
			double lat = listjson.getJSONObject(i).getDouble("lat");
			double lng = listjson.getJSONObject(i).getDouble("lng");
			
			for(int j = 0 ; j < listjson.length() ; j ++)
			{
				double lat1 = listjson.getJSONObject(j).getDouble("lat");
				double lng1 = listjson.getJSONObject(j).getDouble("lng");
				if(lat1 == lat && lng1 == lng && i !=j )
				{
					System.out.println("第"+i+"个的第"+j + "出现重复项");
					System.out.println(lat1 + "  -  " + lng1 + "   上次抓次数" + listjson.getJSONObject(j).getInt("last"));
					System.out.println(lat + "  -  " + lng + "   上次抓次数"+ listjson.getJSONObject(i).getInt("last"));
					index ++ ;
				}
			}
		}
		System.out.println("总次数为 "+ listjson.length());
		System.out.println("重复次数为 "+ index);
		
	}
	*/
		ArrayList<double[]> ret = grabPosition.returnposition();
		System.out.println(ret.size());
		Map<String,double[]> m = new HashMap<String,double[]>() ;
		for(int i = 0 ; i < ret.size() ; i ++)
		{
			m.put(wkt_code(ret.get(i)[0], ret.get(i)[1], 5),ret.get(i) );
			
		}
		for(Object obj : m.keySet()){
			Object value = m.get(obj );
			System.out.println(value);
			}
		System.out.println(m.size());
		
		/*ArrayList<double[]> ret = grabPosition.returnposition();
		ArrayList<double[]> ret1 = grabPosition.returnposition();
		System.out.println(ret.size());
		
		int index = 0 ;
		for(int i = 0 ; i < ret.size() ; i ++)
		{
			double lat = ret.get(i)[0];
			double lng = ret.get(i)[1];

			double lat1 = ret1.get(i)[0];
			double lng1 = ret1.get(i)[1];
		if(lat == lat1 && lng == lng1)
		{
		
					index ++ ;
		}
		
		}
		System.out.println("总次数为 "+ ret.size() + "总次数为" + ret1.size());
		System.out.println("重复次数为 "+ index);	*/
		}
	
	}


