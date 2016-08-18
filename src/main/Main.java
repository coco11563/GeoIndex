package main;
import java.util.LinkedList;

import datastruct.*;
import json.*;
import method.*;
import module.*;
public class Main {
public static void main(String args[])
{
	LinkedList < AreaData > location = new LinkedList < AreaData > ( );
	location.add ( new AreaData ( 39, 41, 111, 118.5 ) );// 京津唐冀地区
	location.add ( new AreaData ( 27.5, 32, 120.5, 122 ) );// 沪杭
	location.add ( new AreaData ( 22, 24.25, 107, 114.5 ) );// 两广
	location.add ( new AreaData ( 24.25, 37, 117.75, 119 ) );
	location.add ( new AreaData ( 27.25, 37, 119, 120.5 ) );
	location.add ( new AreaData ( 24.25, 31, 107, 117.75 ) );// 大地区
	location.add ( new AreaData ( 31, 39, 107, 117.75 ) );// 大地区
	location.add ( new AreaData ( 43, 48, 120, 131 ) );// 东三省
	location.add ( new AreaData ( 41, 43, 114, 126 ) );// 东三省
	location.add ( new AreaData ( 40, 41, 122.25, 124.5 ) );// 东三省
	location.add ( new AreaData ( 38.75, 39, 121.25, 122.25 ) );// 东三省
	location.add ( new AreaData ( 27, 32.5, 103, 107 ) );// 川黔渝
	location.add ( new AreaData ( 23.5, 27, 99, 107 ) );// 滇
	
	//20151113 licong
	location.add ( new AreaData ( 43.65, 44.5, 87.2, 87.8 ) );//乌鲁木齐
	location.add ( new AreaData ( 32.5, 42.5, 98, 107 ) );//青海
	location.add ( new AreaData ( 40.3, 40.7, 79.7, 82 ) );//阿克苏
	location.add ( new AreaData ( 40.7, 41.4, 79.9, 80.7 ) );//
	location.add ( new AreaData ( 41.2, 41.8, 85.7, 86.3 ) );//巴音郭楞
	location.add ( new AreaData ( 41.8, 42.3, 86.1, 87.3 ) );//
	location.add ( new AreaData ( 44.8, 45, 82, 82.55 ) );//阿勒泰
	location.add ( new AreaData ( 47.75, 47.95, 88.1, 88.2 ) );//
	location.add ( new AreaData ( 37, 37.5, 79.4, 80.4 ) );//和田
	location.add ( new AreaData ( 29.6, 29.75, 90.9, 91.45 ) );//拉萨
	
	//20151118 licong
	location.add ( new AreaData ( 18.1, 20.15, 108.5, 111 ) );// 海南
	location.add ( new AreaData ( 22.5, 24.25, 120, 120.5 ) );// 台湾
	location.add ( new AreaData ( 22, 24.75, 120.5, 121 ) );// 台湾
	location.add ( new AreaData ( 22.75, 25.25, 121, 121.5 ) );// 台湾
	location.add ( new AreaData ( 24.25, 25.25, 121.5, 122 ) );// 台湾
	KDTree kdtree = new KDTree(2);
	double lat_min,lat_max,lon_min,lon_max ;
	int index = 0;
	for ( int j = 0; j < location.size ( ); j++ )
	{
		AreaData lat_lon = location.get ( j );
		lat_min = lat_lon.getLat_min ( );
		lon_min = lat_lon.getLon_min ( );
		lat_max = lat_lon.getLat_max ( );
		lon_max = lat_lon.getLon_max ( );
		double	latlon					= 0.1;//11132M＝0.1度
		
		for ( int i = 0; i < 2; i++ )
		{
			if ( i == 1 )
			{
				lat_min 	= lat_min 	+ latlon;
				lon_min 	= lon_min 	+ latlon;
				lat_max 	= lat_max	- latlon;
				lon_max 	= lon_max 	- latlon;
			}
			for ( double lat = lat_min; lat < lat_max - latlon; lat = lat + 2*latlon )
			{
				for ( double lon = lon_min; lon < lon_max - latlon; lon = lon + 2*latlon )
				{
					System.out.println(lon+" " +lat);
					double[] coordinary = new double[2];
					coordinary[0] = lat ; 
					coordinary[1] = lon ;
					try {
						kdtree.insert(coordinary, index++);
					} catch (KeySizeException | KeyDuplicateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			double [] request = new double[2] ;
			request[0] = 24.95;
			request[1] = 121.8;
			
			System.out.println(12312312);
			try {
				System.out.println(kdtree.nearest(request));
			} catch (KeySizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	System.out.println(index);
	}
	}
}
