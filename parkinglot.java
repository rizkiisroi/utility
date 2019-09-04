    package id.loc;
/*
creator: Alamsyah Rizki Isroi (alamsyah.isroi@bankfab.com)
usage: for parkingLot dummy
will allocate directory tmp
[lot_number]#[status].lot
1#empty.lot
2#[car_plat]_color.lot
*/
	import java.util.*;
	import java.io.*;
	import java.util.regex.Pattern;
	import java.util.regex.Matcher;
	
//
    public class parkinglot {
		
// main method		
		public static void main(String []args){
			System.out.println("Java runtime started");
			
//			printUsage(); // calling printUsage method
			
			Scanner scan = new Scanner(System.in);
//			String val = scan.nextLine();
			String val="";
			
//			boolean test;
			while (!(val.equals("exit"))){
				System.out.println("\n");
				printUsage(); // calling printUsage method
				System.out.println("##############################################");
				System.out.println("Please input your action:");
				System.out.println("##############################################");
				val = scan.nextLine(); //prompt user to input
//				test = abs_val.equals(exit);
//				System.out.println("You just inputted: "+val);
//				System.out.println("boolean value: "+test);
//				if (abs_val.equals == "exit"){
				if (val.equals("exit")){ //String comparison in java using equals instead of ==
					System.out.println("exiting...");
					break;
				} else {
//					System.out.println("Calling mainLogic with parameter: "+val);
					mainLogic(val); // calling mainLogic method
				}
			}
			
			scan.close();
			
			System.out.println("Java runtime ended");
			return;			
		}

// print usage
		public static void printUsage(){
			System.out.println("\n");
			System.out.println("##################################");
			System.out.println("######## Parking Lot Usage #######");
			System.out.println("##################################");
			System.out.println("create_parking_lot [number]");
			System.out.println("park [car_plat] [color] [lot_number]");
			System.out.println("leave [car_plat]");
			System.out.println("status");
			System.out.println("flush");
			System.out.println("lot [car_plat]");
			System.out.println("plat [lot_number]");
			System.out.println("color [lot_number]");
			System.out.println("search_color [color]");
			System.out.println("exit");
			System.out.println("##################################\n");
			
			return;
		}
		
// main logic to check user inputted command and parameter
		public static void mainLogic(String param){
			
			String[] array = param.split("\\s+");
			int arr_len = array.length;
//			System.out.println("Array length "+arr_len);
			if(arr_len > 4){
				System.out.println("Invalid command or parameter supplied");
			} else {
				String command = array[0];
//				System.out.println("Your command is "+command);
				switch(arr_len){
					case 1:
						if(command.equals("status")){
							System.out.println("Checking parking_lot status...");
							statusParkingLot(); //check status parkingLot
						} else if (command.equals("flush")){
							System.out.println("Flushing parking_lot...");
							flushParkingLot(); //flushing parkingLot
						} else if (command.equals("exit")){
							System.out.println("exiting...");
						} else {
							System.out.println("Invalid command "+command);
						}
						break;
					case 2:
						if(command.equals("create_parking_lot")){
							try{
								System.out.println("Creating parking_lot... "+array[1]);
								createParkingLot(Integer.parseInt(array[1])); //creating parkingLot
							} catch (Exception ex) {
								System.out.println("Invalid parameter: "+array[1]);
							}
						} else if (command.equals("leave")){
							System.out.println("Leaving parking_lot... "+array[1]);
							leaveParkingLot(array[1]);
						} else if (command.equals("lot")){
							System.out.println("Checking lot... "+array[1]);
							lotParkingLot(array[1]); //check car's lot_number by plat
						} else if (command.equals("plat")){
							try{
								System.out.println("Checking plat... "+array[1]);
								platParkingLot(Integer.parseInt(array[1])); //check car's plat by lot_number
							} catch (Exception ex) {
								System.out.println("Invalid parameter: "+array[1]);
							}
						} else if (command.equals("color")){
							try{
								System.out.println("Checking color... "+array[1]);
								colorParkingLot(Integer.parseInt(array[1])); //check car's color by lot_number
							} catch (Exception ex) {
								System.out.println("Invalid parameter: "+array[1]);
							}
						} else if (command.equals("search_color")){
							System.out.println("Searching by color "+array[1]);
							searchColorParkingLot(array[1]); //search by color
						} else {
							System.out.println("Invalid command or parameter supplied");
						}
						break;
					case 4:
						if(command.equals("park")){
							//"park [car_plat] [color] [lot_number]"
							System.out.println("Parking... "+array[1]+" "+array[2]+" "+array[3]);
							String carPlat=String.valueOf(array[1]);
							String carColor=String.valueOf(array[2]);
							try{
								int carLot = Integer.parseInt(array[3]);
								parkingCar(carPlat, carColor, carLot); //allocate parkingLot
							} catch (Exception ex) {
								System.out.println("3th argument must be integer for lot number: "+array[3]);
							}
						} else {
							System.out.println("Invalid command or parameter supplied");
						}
						break;
					default:
						System.out.println("Invalid command or parameter supplied");
						break;
				}
				
			}
			
			return;
		}
		
//
// create parking lot
        public static void createParkingLot(int lot) {

			File theDir = new File("tmp"); // create tmp directory to store .lot file
			
			// if the directory does not exist, create it
			if (!theDir.exists()) {
//				System.out.println("creating directory: " + theDir.getName());
				boolean result = false;
			
				try{
					theDir.mkdir();
					result = true;
				} catch(SecurityException se){
					//handle it
				}        
				if(result) {    
					System.out.println("DIR created");  
				}
			}
			
			File[] listOfFiles = theDir.listFiles();
			
			int tot_files = listOfFiles.length;
			Date date=new Date();
			
			if(tot_files>0){
				System.out.println("parking_lots already created, there are "+tot_files+" lots");
			} else {
				for(int i=1; i<=lot; i++){
					try {
						String full_file_name = "tmp/"+i+"#empty.lot";
						OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(full_file_name), "UTF-8");
						BufferedWriter bufWriter = new BufferedWriter(writer);
						bufWriter.write(date.toString());
						bufWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Created "+lot+" parking_lots");
			}
				
			return;
		}

//
// check status of parking lot
        public static void statusParkingLot() {

			File theDir = new File("tmp"); // create tmp directory to store .lot file
			
			// if the directory does not exist, create it
			if (!theDir.exists()) {
				System.out.println("parking_lot not created yet, please create_parking_lot first");
			} else {
				File[] listOfFiles = theDir.listFiles();
				int tot_files = listOfFiles.length;
				
				if(tot_files>0){
					System.out.println("parking_lots already created, there are "+tot_files+" lots");
					int lot_empty=0;
					int lot_occupied=0;
					
					String patternString = ".*#empty.*";
			
					Pattern pattern = Pattern.compile(patternString);
			
					Matcher matcher=null;
					boolean matches;
					
					String itemPlat=null;
					String itemColor=null;
					String itemTime=null;
		
					System.out.println("##############################################");
					System.out.println("Lot_no \t plat \t color \t time_occupied/empty");
					System.out.println("##############################################");
										
					for (File file : listOfFiles) {
						if (file.isFile()) {
							String file_name="";
							file_name = file.getName();
							matcher = pattern.matcher(file_name);
							matches = matcher.matches();
							String [] tmpArray = file_name.split("#");
							
							String relPath = "tmp/"+file_name;
							try {
								// FileReader reads text files in the default encoding.
								FileReader fileReader = new FileReader(relPath);
							
								// Always wrap FileReader in BufferedReader.
								BufferedReader bufferedReader = new BufferedReader(fileReader);
								itemTime = bufferedReader.readLine();
//								while((line = bufferedReader.readLine()) != null) {
//									System.out.println(line);
//								}   
								// Always close files.
								bufferedReader.close();         
							} catch(FileNotFoundException ex) {
								System.out.println("Unable to open file '" + relPath + "'");                
							} catch(IOException ex) {
								System.out.println("Error reading file '" + relPath + "'");                  
								// Or we could just do this: 
								// ex.printStackTrace();
							}
							
							if (matches){
								lot_empty += 1;
								System.out.println(tmpArray[0]+" \t empty \t empty \t "+itemTime);
							} else {
								if(tmpArray.length>=1){
									String [] carPlat = tmpArray[1].split("_");
									itemPlat = carPlat[0];
									if (carPlat.length>=1){
										String [] carColor = carPlat[1].split(".lot");
										itemColor = carColor[0];
									}
								}
								System.out.println(tmpArray[0]+" \t "+itemPlat+" \t "+itemColor+" \t "+itemTime);
								lot_occupied += 1;
							}
//							System.out.println(file.getName());
						}
					}
					System.out.println("##############################################");
					System.out.println("\n");
					System.out.println("There is/are "+lot_empty+" lot/s available");
					System.out.println("There is/are "+lot_occupied+" lot/s occupied");
					System.out.println("\n");
					System.out.println("##############################################");
				} else {
					System.out.println("parking_lot not created yet, please create_parking_lot first");
				}

			}
				
			return;
		}

//
// flush the parking lot
        public static void flushParkingLot() {

			File theDir = new File("tmp"); // load tmp directory
			
			// try to delete tmp folder
			try{
				String[]entries = theDir.list();
				for(String s: entries){
					File currentFile = new File(theDir.getPath(),s);
					currentFile.delete();
				}
				theDir.delete();
				System.out.println("Successfully flush parking_lot");
			} catch (Exception ex) {
				System.out.println("Error while flush parking_lot");
				ex.printStackTrace();
			}
				
			return;
		}

//
// parking the car
        public static void parkingCar(String parkingPlat, String parkingColor, int parkingNo) {

			File theDir = new File("tmp"); // create tmp directory to store .lot file
			
			// check directory existance
			if (!theDir.exists()) {
				System.out.println("parking_lot not created yet, please create_parking_lot first");
			} else {
				File theLot = new File("tmp/"+parkingNo+"#empty.lot");
				
				if(theLot.delete()){
					try{
						Date date=new Date();
	
						String full_file_name = "tmp/"+parkingNo+"#"+parkingPlat+"_"+parkingColor+".lot";
						OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(full_file_name), "UTF-8");
						BufferedWriter bufWriter = new BufferedWriter(writer);
						bufWriter.write(date.toString());
						bufWriter.close();
						System.out.println("Successfully allocate lot number "+parkingNo+" for "+parkingPlat);
						
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("Unable to allocate lot number "+parkingNo+" for "+parkingPlat);
					}
				} else {
					System.out.println("lot_number "+parkingNo+" not existed or occupied");
				}				

			}
				
			return;
		}
		
//
// leaving parkingLot of particular car
        public static void leaveParkingLot(String parkingPlat) {

			File theDir = new File("tmp"); // create tmp directory to store .lot file
			
			// if the directory does not exist, create it
			if (!theDir.exists()) {
				System.out.println("parking_lot not created yet, please create_parking_lot first");
			} else {
				File[] listOfFiles = theDir.listFiles();
				int tot_files = listOfFiles.length;
				
				if(tot_files>0){
					
					String patternString = ".*#"+parkingPlat+".*";
			
					Pattern pattern = Pattern.compile(patternString);
			
					Matcher matcher=null;
					boolean matches;
					Date date=new Date();
					int found=0;
		
					for (File file : listOfFiles) {
						if (file.isFile()) {
							String file_name="";
							file_name = file.getName();
							matcher = pattern.matcher(file_name);
							matches = matcher.matches();
							if (matches){
								File theLot = new File("tmp/"+file_name);
								
								String [] tmpArray = file_name.split("#");
								try{
									int lotNo = Integer.parseInt(tmpArray[0]);
									
									if(theLot.delete()){
										try {
											String full_file_name = "tmp/"+lotNo+"#empty.lot";
											OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(full_file_name), "UTF-8");
											BufferedWriter bufWriter = new BufferedWriter(writer);
											bufWriter.write(date.toString());
											bufWriter.close();
											System.out.println("Successfully release lot_number "+lotNo);
											found=1;
										} catch (IOException e) {
											e.printStackTrace();
										}

									} else {
										System.out.println("unable to clear "+file_name);
									}				
								} catch (Exception ex){
									System.out.println("Invalid storage of "+file_name);
								}

								break;
							} 
						}
					}
					if (found==0)
						System.out.println("car_plat "+parkingPlat+" not found");
				} else {
					System.out.println("parking_lot not created yet, please create_parking_lot first");
				}

			}
				
			return;
		}

//
// lot parkingLot of search lot number with car plat
        public static void lotParkingLot(String parkingPlat) {

			File theDir = new File("tmp"); // create tmp directory to store .lot file
			
			// if the directory does not exist, create it
			if (!theDir.exists()) {
				System.out.println("parking_lot not created yet, please create_parking_lot first");
			} else {
				File[] listOfFiles = theDir.listFiles();
				int tot_files = listOfFiles.length;
				
				if(tot_files>0){
					
					String patternString = ".*#"+parkingPlat+".*";
			
					Pattern pattern = Pattern.compile(patternString);
			
					Matcher matcher=null;
					boolean matches;
					int found=0;
		
					for (File file : listOfFiles) {
						if (file.isFile()) {
							String file_name="";
							file_name = file.getName();
							matcher = pattern.matcher(file_name);
							matches = matcher.matches();
							if (matches){
								File theLot = new File("tmp/"+file_name);
								
								String [] tmpArray = file_name.split("#");
								try{
									int lotNo = Integer.parseInt(tmpArray[0]);
									System.out.println("Car "+parkingPlat+" is available on lot_number "+lotNo);
									found=1;
								} catch (Exception ex){
									System.out.println("Invalid storage of "+file_name);
								}
								break;
							} 
						}
						if (found==1)
							break;
					}
					if (found!=1)
						System.out.println("Car "+parkingPlat+" is not found on any lot_number");;
				} else {
					System.out.println("parking_lot not created yet, please create_parking_lot first");
				}

			}
				
			return;
		}

//
// lot parkingLot of search lot number with car plat
        public static void platParkingLot(int lotNo) {

			File theDir = new File("tmp"); // create tmp directory to store .lot file
			
			// if the directory does not exist, create it
			if (!theDir.exists()) {
				System.out.println("parking_lot not created yet, please create_parking_lot first");
			} else {
				File[] listOfFiles = theDir.listFiles();
				int tot_files = listOfFiles.length;
				
				if(tot_files>0){
					
					String patternString = lotNo +"#.*";
			
					Pattern pattern = Pattern.compile(patternString);
			
					Matcher matcher=null;
					boolean matches;
					int found=0;
		
					for (File file : listOfFiles) {
						if (file.isFile()) {
							String file_name="";
							file_name = file.getName();
							matcher = pattern.matcher(file_name);
							matches = matcher.matches();
							if (matches){
								File theLot = new File("tmp/"+file_name);
								
								String [] tmpArray = file_name.split("#");
								if (tmpArray[1].equals("empty.lot")) {
									System.out.println("lot_number "+lotNo+" is empty");
								} else {
									String [] platCar = tmpArray[1].split("_");
									System.out.println("car_plat is "+platCar[0]);
									found=1;
								}
							} 
						}
						if (found==1)
							break;
					}
					if (found!=1)
						System.out.println("Lot "+lotNo+" is not valid");;
				} else {
					System.out.println("parking_lot not created yet, please create_parking_lot first");
				}

			}
				
			return;
		}

//
// lot parkingLot of search lot number with car plat
        public static void colorParkingLot(int lotNo) {

			File theDir = new File("tmp"); // create tmp directory to store .lot file
			
			// if the directory does not exist, create it
			if (!theDir.exists()) {
				System.out.println("parking_lot not created yet, please create_parking_lot first");
			} else {
				File[] listOfFiles = theDir.listFiles();
				int tot_files = listOfFiles.length;
				
				if(tot_files>0){
					
					String patternString = lotNo +"#.*";
			
					Pattern pattern = Pattern.compile(patternString);
			
					Matcher matcher=null;
					boolean matches;
					int found=0;
		
					for (File file : listOfFiles) {
						if (file.isFile()) {
							String file_name="";
							file_name = file.getName();
							matcher = pattern.matcher(file_name);
							matches = matcher.matches();
							if (matches){
								File theLot = new File("tmp/"+file_name);
								
								String [] tmpArray = file_name.split("#");
								if (tmpArray[1].equals("empty.lot")) {
									System.out.println("lot_number "+lotNo+" is empty");
								} else {
									String [] platCar = tmpArray[1].split("_");
									String [] colorCar = platCar[1].split(".lot");
									System.out.println("car_color is "+colorCar[0]);
									found=1;
								}
							} 
						}
						if (found==1)
							break;
					}
					if (found==0)
						System.out.println("Lot "+lotNo+" is not valid");;
				} else {
					System.out.println("parking_lot not created yet, please create_parking_lot first");
				}

			}
				
			return;
		}

//
// search by color
        public static void searchColorParkingLot(String car_color) {

			File theDir = new File("tmp"); // create tmp directory to store .lot file
			
			// check directory existance
			if (!theDir.exists()) {
				System.out.println("parking_lot not created yet, please create_parking_lot first");
			} else {
				File[] listOfFiles = theDir.listFiles();
				int tot_files = listOfFiles.length;
				
				if(tot_files>0){
					System.out.println("Searching by car color "+car_color);
					int counter=0;
					
					String patternString = ".*_"+car_color+".lot"; //3#AD123_WHITE.lot
			
					Pattern pattern = Pattern.compile(patternString);
			
					Matcher matcher=null;
					boolean matches;
					
					String itemPlat=null;
					String itemColor=null;
					String itemTime=null;
		
					System.out.println("##############################################");
					System.out.println("Lot_no \t plat \t color \t time_occupied/empty");
					System.out.println("##############################################");
										
					for (File file : listOfFiles) {
						if (file.isFile()) {
							String file_name="";
							file_name = file.getName();
							matcher = pattern.matcher(file_name);
							matches = matcher.matches();
							String [] tmpArray = file_name.split("#");
							
							String relPath = "tmp/"+file_name;
							try {
								// FileReader reads text files in the default encoding.
								FileReader fileReader = new FileReader(relPath);
							
								// Always wrap FileReader in BufferedReader.
								BufferedReader bufferedReader = new BufferedReader(fileReader);
								itemTime = bufferedReader.readLine();
//								while((line = bufferedReader.readLine()) != null) {
//									System.out.println(line);
//								}   
								// Always close files.
								bufferedReader.close();         
							} catch(FileNotFoundException ex) {
								System.out.println("Unable to open file '" + relPath + "'");                
							} catch(IOException ex) {
								System.out.println("Error reading file '" + relPath + "'");                  
								// Or we could just do this: 
								// ex.printStackTrace();
							}
							
							if (matches){
								if(tmpArray.length>=1){
									String [] carPlat = tmpArray[1].split("_");
									itemPlat = carPlat[0];
									if (carPlat.length>=1){
										String [] carColor = carPlat[1].split(".lot");
										itemColor = carColor[0];
									}
								}
								System.out.println(tmpArray[0]+" \t "+itemPlat+" \t "+itemColor+" \t "+itemTime);
								counter += 1;
							}
//							System.out.println(file.getName());
						}
					}
					System.out.println("##############################################");
					System.out.println("There is/are "+counter+" car/s with color "+ car_color);
					System.out.println("##############################################");
				} else {
					System.out.println("parking_lot not created yet, please create_parking_lot first");
				}

			}
			
			return;
		}

		
    }
