package com.xixixi.sweetweather;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.RangeCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.tools.ZoomEvent;
import org.achartengine.tools.ZoomListener;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class GraphicActivity extends Activity {

	
    double[] minValues = new double[] { -24, -19, -10, -1, 7, 12, 15, 14, 9, 1, -11, -16 };
    double[] maxValues = new double[] { 7, 12, 24, 28, 33, 35, 37, 36, 28, 19, 11, 4 };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graphic);
		
		
		
		XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset(); 
		GraphicalView mChartView;  
		RangeCategorySeries series = new RangeCategorySeries("Temperature");
		
		int length = minValues.length;
	    for (int k = 0; k < length; k++) {
	        series.add(minValues[k], maxValues[k]);
	      }
	    mDataset.addSeries(series.toXYSeries());
	    int[] colors = new int[] { Color.CYAN };
	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    for (int i = 0; i < colors.length; i++) {
	      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	      r.setColor(colors[i]);
	      renderer.addSeriesRenderer(r);
	    }
	    setChartSettings(renderer, "Monthly temperature range", "Month", "Celsius degrees", 0.5, 12.5,
	            -30, 45, Color.GRAY, Color.LTGRAY);
	    
	    renderer.setBarSpacing(0.5);
	    renderer.setXLabels(0);
	    renderer.setYLabels(10);
	    renderer.addXTextLabel(1, "Jan");
	    renderer.addXTextLabel(3, "Mar");
	    renderer.addXTextLabel(5, "May");
	    renderer.addXTextLabel(7, "Jul");
	    renderer.addXTextLabel(10, "Oct");
	    renderer.addXTextLabel(12, "Dec");
	    
	    renderer.addYTextLabel(-25, "Very cold");
	    renderer.addYTextLabel(-10, "Cold");
	    renderer.addYTextLabel(5, "OK");
	    renderer.addYTextLabel(20, "Nice");

	    renderer.setMargins(new int[] {30, 70, 10, 0});
	    renderer.setYLabelsAlign(Align.RIGHT);
	    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
	    r.setDisplayChartValues(true);
	    r.setChartValuesTextSize(12);
	    r.setChartValuesSpacing(3);
	    r.setGradientEnabled(true);
	    r.setGradientStart(-20, Color.RED);
	    r.setGradientStop(20, Color.BLUE);
	    
	    
	    
	    
	    
	    
	    LinearLayout layout = (LinearLayout)findViewById(R.id.graphic_content);
	    mChartView = ChartFactory.getRangeBarChartView(this, mDataset, renderer,Type.DEFAULT); 
	    renderer.setClickEnabled(false);//设置图表是否允许点击  
		
	    mChartView.addZoomListener(new ZoomListener() {  
	          public void zoomApplied(ZoomEvent e) {  
	            String type = "out";  
	            if (e.isZoomIn()) {  
	              type = "in";  
	            }  
	            System.out.println("Zoom " + type + " rate " + e.getZoomRate());  
	          }  
	            
	          public void zoomReset() {  
	            System.out.println("Reset");  
	          }  
	        }, true, true);  
	        //-->end  
 
	        layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));  
	      
	    
	    
	}
	
	
	  protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
		      String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
		      int labelsColor) {
		    renderer.setChartTitle(title);
		    renderer.setXTitle(xTitle);
		    renderer.setYTitle(yTitle);
		    renderer.setXAxisMin(xMin);
		    renderer.setXAxisMax(xMax);
		    renderer.setYAxisMin(yMin);
		    renderer.setYAxisMax(yMax);
		    renderer.setAxesColor(axesColor);
		    renderer.setLabelsColor(labelsColor);
		  }

}
