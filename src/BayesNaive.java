import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Data {
	int classA;
	double averageHighSchool;
	String typeHighSchool;
	double averageFirstYear;
	int examsLeft;
	
	public Data(int classA, double averageHighSchool, String typeHighSchool, double averageFirstYear, int examsLeft) {
		//super();
		this.classA = classA;
		this.averageHighSchool = averageHighSchool;
		this.typeHighSchool = typeHighSchool;
		this.averageFirstYear = averageFirstYear;
		this.examsLeft = examsLeft;
	}
	
	public String toString() {
		String className;
		String examsLeftString;
		if (classA==1){
			className="Diplomiral na vreme";
		}else if (classA==2) {
			className = "Diplomiral so zadocnuvanje";
		}
		else 
			className = "Ne diplomiral";
		
		if (examsLeft==0)
			examsLeftString="0";
		else if (examsLeft==1)
			examsLeftString="od 1-2";
		else if (examsLeft==2)
			examsLeftString="od 3-5";
		else
			examsLeftString="poveke od 5";
		
		
		return String.format("%s, %f, %s, %f, %s", className, averageHighSchool, typeHighSchool, averageFirstYear, examsLeftString);
	}
	
	
}


public class BayesNaive {
	
	public static double NormalDistribution (double x, double mean, double variance) {
		return 1/(Math.sqrt(2*Math.PI*variance)) * Math.exp(-(x-mean)*(x-mean)/(2*variance));
	}
	
	public static void stats (List<Data> dataset) {
		StringBuilder sb = new StringBuilder();		
	}

	public static void main(String[] args) throws FileNotFoundException {
		List<Data> dataset = new ArrayList<>();
		Scanner sc = new Scanner(new FileInputStream(new File("C:\\Users\\Stefan\\Desktop\\ML\\input.txt")));
		
		while (sc.hasNext()){
			String s = sc.nextLine();
			String [] inputs = s.split(",");
			
			Data d = new Data(Integer.parseInt(inputs[0]),Double.parseDouble(inputs[1]), inputs[2],Double.parseDouble(inputs[3]),Integer.parseInt(inputs[4]));
			dataset.add(d);
		}
		
		double naVremeMeanS;
		double naVremeVarianceS;
		double soZadocnuvanjeMeanS;
		double soZadocnuvanjeVarianceS;
		double neDiplomiralMeanS;
		double neDiplomiralVarianceS;
		
		
		naVremeMeanS = dataset.stream().filter(x -> x.classA==1).mapToDouble(x -> x.averageHighSchool).average().getAsDouble();
		soZadocnuvanjeMeanS = dataset.stream().filter(x -> x.classA==2).mapToDouble(x -> x.averageHighSchool).average().getAsDouble();
		neDiplomiralMeanS = dataset.stream().filter(x -> x.classA==3).mapToDouble(x -> x.averageHighSchool).average().getAsDouble();
		naVremeVarianceS = dataset.stream().filter(x -> x.classA==1).mapToDouble(x -> (x.averageHighSchool-naVremeMeanS)*(x.averageHighSchool-naVremeMeanS)).sum()/
				(dataset.stream().filter(x -> x.classA==1).count()-1);
		
		
		
		soZadocnuvanjeVarianceS = 0;
		neDiplomiralVarianceS = 0;
		for (Data d : dataset){
			if (d.classA==2){
				soZadocnuvanjeVarianceS+=((d.averageHighSchool-soZadocnuvanjeMeanS)*(d.averageHighSchool-soZadocnuvanjeMeanS));
			}
			if (d.classA==3){
				neDiplomiralVarianceS+=((d.averageHighSchool-neDiplomiralMeanS)*(d.averageHighSchool-neDiplomiralMeanS));
			}
		}
		soZadocnuvanjeVarianceS /= (dataset.stream().filter(x -> x.classA==2).count()-1);
		neDiplomiralVarianceS /=(dataset.stream().filter(x-> x.classA==3).count()-1);
		
		System.out.println(String.format("Prosek vo sredno uciliste:\nStudenti koi diplomirale na vreme: mean: %f variance: %f\n"
				+ "Studenti koi diplomirale so zadocnuvanje: mean: %f variance: %f\n"
				+ "Studenti koi ne diplomirale: mean: %f variance: %f\n", naVremeMeanS,naVremeVarianceS,soZadocnuvanjeMeanS,soZadocnuvanjeVarianceS,
				neDiplomiralMeanS,neDiplomiralVarianceS));
		
		double naVremeMeanF;
		double naVremeVarianceF;
		double soZadocnuvanjeMeanF;
		double soZadocnuvanjeVarianceF;
		double neDiplomiralMeanF;
		double neDiplomiralVarianceF;
		
		naVremeMeanF = dataset.stream().filter(x -> x.classA==1).mapToDouble(x -> x.averageFirstYear).average().getAsDouble();
		soZadocnuvanjeMeanF = dataset.stream().filter(x -> x.classA==2).mapToDouble(x -> x.averageFirstYear).average().getAsDouble();
		neDiplomiralMeanF = dataset.stream().filter(x -> x.classA==3).mapToDouble(x -> x.averageFirstYear).average().getAsDouble();
		naVremeVarianceF = dataset.stream().filter(x -> x.classA==1).mapToDouble(x -> (x.averageFirstYear-naVremeMeanF)*(x.averageFirstYear-naVremeMeanF)).sum()/
				(dataset.stream().filter(x -> x.classA==1).count()-1);
		soZadocnuvanjeVarianceF = dataset.stream().filter(x -> x.classA==2).mapToDouble(x -> Math.pow(x.averageFirstYear - soZadocnuvanjeMeanF,2)).sum()/
				(dataset.stream().filter(x -> x.classA==2).count()-1);
		
		
		neDiplomiralVarianceF = 0;
		
		for (Data d : dataset){
			if (d.classA==3){
				neDiplomiralVarianceF += ((d.averageFirstYear-neDiplomiralMeanF)*(d.averageFirstYear-neDiplomiralMeanF));
			}
			
		}
		neDiplomiralVarianceF /= (dataset.stream().filter(x -> x.classA==3).count() -1) ;
		
		System.out.println(String.format("Prosek vo prva godina fakultet:\nStudenti koi diplomirale na vreme: mean: %f variance: %f\n"
				+ "Studenti koi diplomirale so zadocnuvanje: mean: %f variance: %f\n"
				+ "Studenti koi ne diplomirale: mean: %f variance: %f\n", naVremeMeanF,naVremeVarianceF,soZadocnuvanjeMeanF,soZadocnuvanjeVarianceF,
				neDiplomiralMeanF,neDiplomiralVarianceF));
		
		double naVremeGimnaziskoP;
		double naVremeStrucnoP;
		double soZadocnuvanjeGimnaziskoP;
		double soZadocnuvanjeStrucnoP;
		double neDiplomiralGimnaziskoP;
		double neDiplomiralStrucnoP;
		
		naVremeGimnaziskoP = (double) dataset.stream().filter(x -> x.classA==1 && x.typeHighSchool.equals("gimnazisko")).count()/
				dataset.stream().filter(x -> x.classA==1).count()*1.0;
		naVremeStrucnoP = (double) dataset.stream().filter(x -> x.classA==1 && x.typeHighSchool.equals("strucno")).count()/
				dataset.stream().filter(x -> x.classA==1).count()*1.0;
		soZadocnuvanjeGimnaziskoP = (double) dataset.stream().filter(x -> x.classA==2 && x.typeHighSchool.equals("gimnazisko")).count()/
				dataset.stream().filter(x -> x.classA==2).count()*1.0;
		soZadocnuvanjeStrucnoP = (double) dataset.stream().filter(x -> x.classA==2 && x.typeHighSchool.equals("strucno")).count()/
				dataset.stream().filter(x -> x.classA==2).count()*1.0;
		neDiplomiralGimnaziskoP = (double) dataset.stream().filter(x -> x.classA==3 && x.typeHighSchool.equals("gimnazisko")).count()/
				dataset.stream().filter(x -> x.classA==3).count()*1.0;
		neDiplomiralStrucnoP = (double) dataset.stream().filter(x -> x.classA==3 && x.typeHighSchool.equals("strucno")).count()/
				dataset.stream().filter(x -> x.classA==3).count()*1.0;
		
		System.out.println(String.format("Verojatnost za tipot na sredno obrazovanie:\nStudenti koi diplomirale na vreme: gimnaziso: %f strucno: %f\n"
				+ "Studenti koi diplomirale so zadocnuvanje: gimanzisko: %f strucno: %f\n"
				+ "Studenti koi ne diplomirale: gimnazisko: %f strucno: %f\n", naVremeGimnaziskoP, naVremeStrucnoP, soZadocnuvanjeGimnaziskoP, 
				soZadocnuvanjeStrucnoP, neDiplomiralGimnaziskoP, neDiplomiralStrucnoP));
		
		
		double vreme0, vreme1, vreme2, vreme3, zadocni0, zadocni1, zadocni2, zadocni3, ne0, ne1, ne2, ne3;
		double totalVreme = dataset.stream().filter(x -> x.classA==1).count();
		double totalZadocnil = dataset.stream().filter(x -> x.classA==2).count();
		double totalNe = dataset.stream().filter(x -> x.classA==3).count();
		vreme0 = (double) dataset.stream().filter(x -> x.classA==1 && x.examsLeft==0).count()/totalVreme*1.0;
		vreme1 = (double) dataset.stream().filter(x -> x.classA==1 && x.examsLeft==1).count()/totalVreme*1.0;
		vreme2 = (double) dataset.stream().filter(x -> x.classA==1 && x.examsLeft==2).count()/totalVreme*1.0;
		vreme3 = (double) dataset.stream().filter(x -> x.classA==1 && x.examsLeft==3).count()/totalVreme*1.0;
		zadocni0 = (double) dataset.stream().filter(x -> x.classA==2 && x.examsLeft==0).count()/totalZadocnil*1.0;
		zadocni1 = (double) dataset.stream().filter(x -> x.classA==2 && x.examsLeft==1).count()/totalZadocnil*1.0;
		zadocni2 = (double) dataset.stream().filter(x -> x.classA==2 && x.examsLeft==2).count()/totalZadocnil*1.0;
		zadocni3 = (double) dataset.stream().filter(x -> x.classA==2 && x.examsLeft==3).count()/totalZadocnil*1.0;
		ne0 = (double) dataset.stream().filter(x -> x.classA==3 && x.examsLeft==0).count()/totalNe*1.0;
		ne1 = (double) dataset.stream().filter(x -> x.classA==3 && x.examsLeft==1).count()/totalNe*1.0;
		ne2 = (double) dataset.stream().filter(x -> x.classA==3 && x.examsLeft==2).count()/totalNe*1.0;
		ne3 = (double) dataset.stream().filter(x -> x.classA==3 && x.examsLeft==3).count()/totalNe*1.0;
		
		//Laplasova transofrmacija
		int m=1; 
		double p=0.25;
		if (vreme0==0.0 || vreme1==0.0 || vreme2 == 0 || vreme3 == 0){
			vreme0 = (double) (dataset.stream().filter(x -> x.classA==1 && x.examsLeft==0).count()+m*p)/(totalVreme+1)*1.0;
			vreme1 = (double) (dataset.stream().filter(x -> x.classA==1 && x.examsLeft==1).count()+m*p)/(totalVreme+1)*1.0;
			vreme2 = (double) (dataset.stream().filter(x -> x.classA==1 && x.examsLeft==2).count()+m*p)/(totalVreme+1)*1.0;
			vreme3 = (double) (dataset.stream().filter(x -> x.classA==1 && x.examsLeft==3).count()+m*p)/(totalVreme+1)*1.0;
			
		}
		if (zadocni0==0.0 || zadocni1==0.0 || zadocni2 == 0.0 || zadocni3 == 0.0){
			zadocni0 = (double) (dataset.stream().filter(x -> x.classA==2 && x.examsLeft==0).count()+m*p)/(totalZadocnil+1)*1.0;
			zadocni1 = (double) (dataset.stream().filter(x -> x.classA==2 && x.examsLeft==1).count()+m*p)/(totalZadocnil+1)*1.0;
			zadocni2 = (double) (dataset.stream().filter(x -> x.classA==2 && x.examsLeft==2).count()+m*p)/(totalZadocnil+1)*1.0;
			zadocni3 = (double) (dataset.stream().filter(x -> x.classA==2 && x.examsLeft==3).count()+m*p)/(totalZadocnil+1)*1.0;
		}
		
		if (ne0==0.0 || ne1==0.0 || ne2 == 0 || ne3 == 0){
			ne0 = (double) (dataset.stream().filter(x -> x.classA==3 && x.examsLeft==0).count()+m*p)/(totalNe+1)*1.0;
			ne1 = (double) (dataset.stream().filter(x -> x.classA==3 && x.examsLeft==1).count()+m*p)/(totalNe+1)*1.0;
			ne2 = (double) (dataset.stream().filter(x -> x.classA==3 && x.examsLeft==2).count()+m*p)/(totalNe+1)*1.0;
			ne3 = (double) (dataset.stream().filter(x -> x.classA==3 && x.examsLeft==3).count()+m*p)/(totalNe+1)*1.0;
		}
		
		//System.out.println(vreme0+vreme1+vreme2+vreme3);
		//System.out.println(zadocni0+zadocni1+zadocni2+zadocni3);
		//System.out.println(ne0+ne1+ne2+ne3);
		
		//System.out.println(NormalDistribution(6,5.855,0.026));
		System.out.println(String.format("Verojatnost za nepolozeni ispiti:\nStudenti koi diplomirale na vreme: "
				+ "0 ispiti: %f od 1-2 ispiti: %f od 3-5 ispiti: %f poveke od 5 ispiti: %f\n"
				+ "Studenti koi diplomirale so zadocnuvanje:"
				+ " 0 ispiti: %f od 1-2 ispiti: %f od 3-5 ispiti: %f poveke od 5 ispiti: %f\n"
				+ "Studenti koi ne diplomirale: "
				+ "0 ispiti: %f od 1-2 ispiti: %f od 3-5 ispiti: %f poveke od 5 ispiti: %f\n", vreme0, vreme1, vreme2, vreme3,
				zadocni0,zadocni1,zadocni2, zadocni3, ne0, ne1, ne2, ne3));	
		
		
		sc.close();
		
		double pNaVreme = (double) totalVreme/dataset.size()*1.0;
		double pSoZadocnuvanje = (double) totalZadocnil/dataset.size()*1.0;
		double pNeDiplomiral = (double) totalNe/dataset.size()*1.0;
		
		
		Scanner scanner = new Scanner(System.in);
		double [] verojatnosti = new double[3];
		
			
			String in = scanner.nextLine();
			
			String [] inputs = in.split(",");
			Data d = new Data(0,Double.parseDouble(inputs[0]),inputs[1],Double.parseDouble(inputs[2]),Integer.parseInt(inputs[3]));
			
			for (int j=0;j<3;j++) {
				
				if (j==0){
					verojatnosti[j]=pNaVreme;
					verojatnosti[j]*=NormalDistribution(d.averageHighSchool,naVremeMeanS,naVremeVarianceS);
					System.out.println(NormalDistribution(d.averageHighSchool,naVremeMeanS,naVremeVarianceS));
					if (d.typeHighSchool=="gimnazisko")
						verojatnosti[j]*=naVremeGimnaziskoP;
					else
						verojatnosti[j]*=naVremeStrucnoP;
					
					verojatnosti[j]*=NormalDistribution(d.averageFirstYear,naVremeMeanF,naVremeVarianceF);
					
					if (d.examsLeft==0)
						verojatnosti[j]*=vreme0;
					else if (d.examsLeft==1)
						verojatnosti[j]*=vreme1;
					else if (d.examsLeft==2)
						verojatnosti[j]*=vreme2;
					else
						verojatnosti[j]*=vreme3;
					
				}
				else if (j==1){
					verojatnosti[j]=pSoZadocnuvanje;
					System.out.println(pSoZadocnuvanje);
					verojatnosti[j]*=NormalDistribution(d.averageHighSchool,soZadocnuvanjeMeanS,soZadocnuvanjeVarianceS);
					if (d.typeHighSchool=="gimnazisko")
						verojatnosti[j]*=soZadocnuvanjeGimnaziskoP;
					else
						verojatnosti[j]*=soZadocnuvanjeStrucnoP;
					
					verojatnosti[j]*=NormalDistribution(d.averageFirstYear,soZadocnuvanjeMeanF,soZadocnuvanjeVarianceF);
					
					if (d.examsLeft==0)
						verojatnosti[j]*=zadocni0;
					else if (d.examsLeft==1)
						verojatnosti[j]*=zadocni1;
					else if (d.examsLeft==2)
						verojatnosti[j]*=zadocni2;
					else
						verojatnosti[j]*=zadocni3;
				}
				else if (j==2){
					verojatnosti[j]=pNeDiplomiral;
					verojatnosti[j]*=NormalDistribution(d.averageHighSchool,neDiplomiralMeanS,neDiplomiralVarianceS);
					if (d.typeHighSchool=="gimnazisko")
						verojatnosti[j]*=neDiplomiralGimnaziskoP;
					else
						verojatnosti[j]*=neDiplomiralStrucnoP;
					
					verojatnosti[j]*=NormalDistribution(d.averageFirstYear,naVremeMeanF,naVremeVarianceF);
					
					if (d.examsLeft==0)
						verojatnosti[j]*=ne0;
					else if (d.examsLeft==1)
						verojatnosti[j]*=ne1;
					else if (d.examsLeft==2)
						verojatnosti[j]*=ne2;
					else
						verojatnosti[j]*=ne3;
				}
				
			}
			
			Arrays.stream(verojatnosti).forEach(x -> x /= Arrays.stream(verojatnosti).map(i->i).sum());
			double maxP = Arrays.stream(verojatnosti).map(x -> x).max().getAsDouble();
			for (int k=0;k<3;k++) {
				System.out.println("Verojatnosta da se sluchi klasa "+ (k+1) + " e: " + verojatnosti[k]);
				if (verojatnosti[k]==maxP){
					d.classA=k+1;
				}
			}
			
			System.out.println(d.toString());
			
			//i++;
		}
		
		
	
	

}
