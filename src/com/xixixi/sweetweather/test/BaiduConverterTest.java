package com.xixixi.sweetweather.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.xixixi.sweetweather.entity.RealtimeWeather;

import com.xixixi.sweetweather.util.BaiduWeatherToPredictionsConverter;
import com.xixixi.sweetweather.util.RealtimeWeatherConverter;

public class BaiduConverterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String json = "{\"error\":0,\"status\":\"success\",\"date\":\"2014-10-05\",\"results\":[{\"currentCity\":\"拜泉\",\"pm25\":\"\",\"index\":[{\"title\":\"穿衣\",\"zs\":\"冷\",\"tipt\":\"穿衣指数\",\"des\":\"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。\"},{\"title\":\"洗车\",\"zs\":\"较适宜\",\"tipt\":\"洗车指数\",\"des\":\"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。\"},{\"title\":\"旅游\",\"zs\":\"适宜\",\"tipt\":\"旅游指数\",\"des\":\"天气较好，但丝毫不会影响您出行的心情。温度适宜又有微风相伴，适宜旅游。\"},{\"title\":\"感冒\",\"zs\":\"易发\",\"tipt\":\"感冒指数\",\"des\":\"昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。\"},{\"title\":\"运动\",\"zs\":\"较不宜\",\"tipt\":\"运动指数\",\"des\":\"天气较好，但考虑天气寒冷，推荐您进行各种室内运动，若在户外运动请注意保暖并做好准备活动。\"},{\"title\":\"紫外线强度\",\"zs\":\"弱\",\"tipt\":\"紫外线强度指数\",\"des\":\"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。\"}],\"weather_data\":[{\"date\":\"周日 10月05日 (实时：7℃)\",\"dayPictureUrl\":\"http://api.map.baidu.com/images/weather/day/qing.png\",\"nightPictureUrl\":\"http://api.map.baidu.com/images/weather/night/qing.png\",\"weather\":\"晴\",\"wind\":\"西南风3-4级\",\"temperature\":\"-1℃\"},{\"date\":\"周一\",\"dayPictureUrl\":\"http://api.map.baidu.com/images/weather/day/qing.png\",\"nightPictureUrl\":\"http://api.map.baidu.com/images/weather/night/duoyun.png\",\"weather\":\"晴转多云\",\"wind\":\"西南风3-4级\",\"temperature\":\"14 ~ 4℃\"},{\"date\":\"周二\",\"dayPictureUrl\":\"http://api.map.baidu.com/images/weather/day/xiaoyu.png\",\"nightPictureUrl\":\"http://api.map.baidu.com/images/weather/night/duoyun.png\",\"weather\":\"小雨转多云\",\"wind\":\"西北风3-4级\",\"temperature\":\"12 ~ -3℃\"},{\"date\":\"周三\",\"dayPictureUrl\":\"http://api.map.baidu.com/images/weather/day/xiaoyu.png\",\"nightPictureUrl\":\"http://api.map.baidu.com/images/weather/night/xiaoyu.png\",\"weather\":\"小雨\",\"wind\":\"西南风3-4级\",\"temperature\":\"11 ~ 3℃\"}]}]}";
		//String result = new BaiduWeatherToPredictionsConverter().predictionsConvertToJson((new BaiduWeatherToPredictionsConverter().jsonConvertToPredictions(json)));
		//System.out.println(json);
		//System.out.println(result);
/*		String datestring = "2014-10-05";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(datestring);
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			System.out.println(sdf.format(c1.getTime()));
			c1.add(Calendar.DATE, 1);
			System.out.println(sdf.format(c1.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
/*		String json = "{\"weatherinfo\":{\"city\":\"西安\",\"cityid\":\"101110101\",\"temp\":\"18\",\"WD\":\"东北风\",\"WS\":\"2级\",\"SD\":\"68%\",\"WSE\":\"2\",\"time\":\"00:00\",\"isRadar\":\"1\",\"Radar\":\"JC_RADAR_AZ9290_JB\",\"njd\":\"10700\",\"qy\":\"973\"}}";
		String result = new RealtimeWeatherConverter().RealtimeWeatherToJson(new RealtimeWeatherConverter().JsonToRealtimeWeather(json));
		System.out.println(json);
		System.out.println(result);*/
		String name = "2013-11-23 23:39";
		int index = name.indexOf(".");
		int total = name.length();
		System.out.println(total + " "+ name.substring(11, total));
	}

}
