package hw2;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class kNearest {
	public static void main(String args[]){
		List<Data> train = new ArrayList<Data>(); 
		train.add(new Data(11,78,12.60,100));//+
		train.add(new Data(11,	88,	10.80,	80));//+
		train.add(new Data(11,	100,	9.70,	30));//-
		train.add(new Data(20,	83,	12.20,	50));//-
		train.add(new Data(8,	100,	14.20,	0));//-
		train.add(new Data(12,	90,	10.50,	90));//+
		train.add(new Data(6,	87,	12.50,	30));//-
		train.add(new Data(18,	82,	12.90,	20	));//+
		train.add(new Data(19,	91,	12.30,	80));//+
		train.add(new Data(21,	92,	9.40,	100));//+
		train.add(new Data(10,	90,	11.70,	64));//+
		train.add(new Data(18,	85,	11.80,	95));//+
		train.add(new Data(20,	93,	11.10,	90));//+
		train.add(new Data(14,	92,	8.30,	40));//+
		train.add(new Data(19,	96,	12.00,	100));//+
		train.add(new Data(13,	100,	11.30,	100));//+
		train.add(new Data(3,	96,	4.80,	100));//-
		train.add(new Data(4,	86,	6.90,	40));//-
		train.add(new Data(3,	89,	7.10,	95));//-
		train.add(new Data(15,	93,	8.10,	100));//-
		train.add(new Data(15,	43,	6.90,	100));//-
		train.add(new Data(6,	60,	7.70,	100));//-
		train.add(new Data(2,	92,	9.00,	100));//-
		
		double[] param = normalize(train);
		double minT = param[0];
		double unitT = param[1];
		double minL = param[2];
		double unitL = param[3];
		double minH = param[4];
		double unitH = param[5];
		double minC = param[6];
		double unitC = param[7];
		for(int i = 0; i < train.size(); i++){
			Data t = train.get(i);
			t.temp = (t.temp - minT) * unitT;
			t.light = (t.light - minL) * unitL;
			t.cloud = (t.cloud - minC) * unitC;
			t.hum = (t.hum - minH) * unitH;
		}
		
		/*double t = 2; //2,0,-4
		double l = 7.1; //7.0,3.9,8.1
		double h = 96;
		double c = 100;
		*/
		
		double t = 0; //2,0,-4
		double l = 3.9; //7.0,3.9,8.1
		double h = 83;
		double c = 100;
		
		/*
		double t = -4; //2,0,-4
		double l = 8.1; //7.0,3.9,8.1
		double h = 88;
		double c = 20;
		*/
		
		t = (t - minT)*unitT;
		l = (l - minT)*unitL;
		h = (h - minH)*unitH;
		c = (c - minC)*unitC;
		Data test = new Data(t,h,l,c);
		kNearest(train, test);
		
	}
	
	public static void kNearest(List<Data> train, Data test){
		//List<List<Data>> result = new ArrayList<List<Data>>();
			for(int i = 0; i < train.size(); i++){
				double distance = 0;
				Data tr = train.get(i);
				distance = Math.pow(tr.temp - test.temp,2) + Math.pow(tr.light - test.light,2) + Math.pow(tr.hum - test.hum,2) + Math.pow(tr.cloud - test.cloud,2);
				distance = Math.pow(distance,0.5);
				//distance = Math.abs(tr.temp - test.temp) + Math.abs(tr.light - test.light) + Math.abs(tr.hum - test.hum) + Math.abs(tr.cloud - test.cloud);
				System.out.println(distance);
			}
	}
	
	public static double[] normalize(List<Data> train){
		double maxTemp = Integer.MIN_VALUE;
		double minTemp = Integer.MAX_VALUE;
		double maxLight = Integer.MIN_VALUE;
		double minLight = Integer.MAX_VALUE;
		double maxHum = Integer.MIN_VALUE;
		double minHum = Integer.MAX_VALUE;
		double maxCloud = Integer.MIN_VALUE;
		double minCloud = Integer.MAX_VALUE;
		for(int i = 0; i < train.size(); i++){
			double t = train.get(i).temp;
			double l = train.get(i).light;
			double h = train.get(i).hum;
			double c = train.get(i).cloud;
			maxTemp = maxTemp > t ? maxTemp : t;
			minTemp = minTemp < t ? minTemp : t;
			maxLight = maxLight > l ? maxLight : l;
			minLight = minLight < l ? minLight : l;
			maxHum = maxHum > h ? maxHum : h;
			minHum = minHum < h ? minHum : h;
			maxCloud = maxCloud > c ? maxCloud : c;
			minCloud = minCloud < c ? minCloud : c;
		}
		double unitTemp = 1 / (maxTemp - minTemp);
		double unitLight = 1 / (maxLight - minLight);
		double unitHum = 1 / (maxHum - minHum);
		double unitCloud = 1 / (maxCloud - minCloud);
		double[] r = {minTemp, unitTemp, minLight, unitLight, minHum, unitHum, minCloud, unitCloud};
		return r;
	}
}

class Data{
	double temp = 0;
	double light = 0;
	double hum = 0;
	double cloud = 0;
	Data(double a, double c, double b, double d){
		temp = a;
		light = b;
		hum = c;
		cloud = d;
	}
}

class graph{
	
}
