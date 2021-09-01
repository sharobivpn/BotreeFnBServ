package com.botree.restaurantapp.commonUtil.test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.botree.restaurantapp.commonUtil.MenuUploadFile;
import com.botree.restaurantapp.model.MenuCategory;
import com.botree.restaurantapp.model.MenuItem;
import com.botree.restaurantapp.model.util.PersistenceListener;
import com.botree.restaurantapp.service.exception.FileUplodException;



public class POITest {

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		dateInsert();
		
		FileInputStream fileInputStream=null;
        
        File file = new File("D:\\2015-12-15\\menu.xlsx");
        
        byte[] bFile = new byte[(int) file.length()];
        
        try {
            //convert file into array of bytes
	    fileInputStream = new FileInputStream(file);
	    fileInputStream.read(bFile);
	    fileInputStream.close();
	       
	    //convert array of bytes into file
	    FileOutputStream fileOuputStream = new FileOutputStream("D:\\2015-12-15\\menu2.xlsx"); 
	    fileOuputStream.write(bFile);
	    fileOuputStream.close();
	       
	    System.out.println("Done");
        }catch(Exception e){
            e.printStackTrace();
        }

	}*/

	public String dataInsert(String filename,int storeid,byte[] data) throws FileUplodException {
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		Set<String> categories=new LinkedHashSet<String>();
		List<MenuUploadFile> listMenu=new ArrayList<MenuUploadFile>();
		String rootObj="Menu";
		String type="r";
		MenuUploadFile menuUploadFile=null;
		String status="failure";
		InputStream is=null;
	
        byte[] bFile = data;
        
        try {
        	is = new ByteArrayInputStream(bFile);
         	//convert array of bytes into file
	    	/*FileOutputStream fileOuputStream = new FileOutputStream("D:\\menu22.xlsx"); 
	    	fileOuputStream.write(bFile);
	    	fileOuputStream.close();
	       */
	    	System.out.println("Done");
        }catch(Exception e){
            e.printStackTrace();
        }

		try {
			int storeId=storeid;
			entityManagerFactory = PersistenceListener.getEntityManager();

			em = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			
			//create root menu
			MenuCategory root=null;
			try {
				
				//check if already root exists
				Query qryRoot = em
						.createQuery("SELECT m FROM MenuCategory m WHERE m.menuCategoryName=:menuCategoryName and m.type=:type and m.storeId=:storeId");
				qryRoot.setParameter("menuCategoryName", rootObj);
				qryRoot.setParameter("type", type);
				qryRoot.setParameter("storeId", storeId);
				root = (MenuCategory) qryRoot.getSingleResult();
				
				
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				 root = new MenuCategory();
				 root.setMenuCategoryName("Menu");
				 root.setStoreId(storeId);
				 root.setDeleteFlag("N");
				 root.setType("r");
				 root.setDirectCat("N");
				 root.setPrintStatus("N");
				 em.persist(root);
				 
				e2.printStackTrace();
			}

			/*FileInputStream file=null;
			
				file = new FileInputStream(new File(
						"D:\\menu22.xlsx"));*/
				
				XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(is));
			
				// Get the workbook instance for XLS file
				//XSSFWorkbook workbook = new XSSFWorkbook(file);

				// Get first sheet from the workbook
				XSSFSheet sheet = workbook.getSheetAt(0);

				 //Iterate through each rows from first sheet
				Iterator<Row> rowIterator = sheet.iterator();
				while(rowIterator.hasNext()) {
				    Row row = rowIterator.next();
				    menuUploadFile=new MenuUploadFile();
				    if(row.getRowNum()!=0){
				     
				    //For each row, iterate through each columns
				    Iterator<Cell> cellIterator = row.cellIterator();
				   
				    while(cellIterator.hasNext()) {
				         
				        Cell cell = cellIterator.next();
				        int colmnIndex=cell.getColumnIndex();
				       System.out.println("column index:: "+ cell.getColumnIndex());
				       
				       if(colmnIndex==0){
				    	   
				    	   //get column value
				    	   double serialNo=cell.getNumericCellValue();
				    	   //String trimmedVal=cateName.trim();
				    	   //categories.add(trimmedVal);
				    	   menuUploadFile.setSerialNo(serialNo);
				       }
				       
				       if(colmnIndex==1){
				    	   
				    	   //get column value
				    	   String cateName=cell.getStringCellValue();
				    	   String trimmedVal=cateName.trim();
				    	   categories.add(trimmedVal);
				    	   menuUploadFile.setCategory(trimmedVal);
				       }
				       if(colmnIndex==2){
				    	   
				    	   //get column value
				    	   String subCateName=cell.getStringCellValue();
				    	   String trimmedVal=subCateName.trim();
				    	   menuUploadFile.setSubCategory(trimmedVal);
				       }
				       if(colmnIndex==3){
				    	   
				    	   //get column value
				    	   String itemname=cell.getStringCellValue();
				    	   String trimmedVal=itemname.trim();
				    	   menuUploadFile.setMenuItem(trimmedVal);
				       }
				       if(colmnIndex==4){
				    	   
				    	   //get column value
				    	   String veg=cell.getStringCellValue();
				    	   String trimmedVal=veg.trim();
				    	   menuUploadFile.setVeg(trimmedVal);
				       }
				       
				       if(colmnIndex==5){
				    	   //get column value
				    	   	double rate=cell.getNumericCellValue();
				    	   //double rate=Double.parseDouble(cell.getStringCellValue());
							//System.out.println("rate is...."+rate);
							menuUploadFile.setRate(rate);
							 
					
				       }
				       
				    
				       }
				    }
				    System.out.println("");
				    if(row.getRowNum()!=0)
				    listMenu.add(menuUploadFile);
				}
			
		   // System.out.println("list size:: "+listMenu.size());
		    //System.out.println("set size:: "+categories.size());
		   // Iterator<String> catIterator=categories.iterator();
		   // while (catIterator.hasNext()) {
		    	//String categoryName = (String) catIterator.next();
		    	/*List<MenuCategory> catList=null;
				
					//check if already category exists
					Query qryCat = em
							.createQuery("SELECT m FROM MenuCategory m WHERE m.menuCategoryName=:menuCategoryName and m.storeId=:storeId");
					qryCat.setParameter("menuCategoryName", categoryName.trim());
					qryCat.setParameter("storeId", storeId);
					catList = (List<MenuCategory>) qryCat.getResultList();
				
					//System.out.println("Category found--->> "+cat.getMenuCategoryName());
				if(catList.size()==0){
					
						//if not exists category
						MenuCategory category=null;
						try {
							category = new MenuCategory();
							category.setMenuCategoryName(categoryName);
							category.setMenutype(root);
							category.setStoreId(storeId);
							category.setDeleteFlag("N");
							category.setType("c");
							category.setBgColor("ffffff");
							category.setDirectCat("N");
							category.setPrintStatus("N");
							em.persist(category);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
						List<MenuUploadFile> errorList=new ArrayList<MenuUploadFile>();
						//loop through menu upload file
						Iterator<MenuUploadFile> iterator=listMenu.iterator();
						while (iterator.hasNext()) {
							MenuCategory category=null;
							MenuUploadFile menuUploadFile2 = (MenuUploadFile) iterator
									.next();
							String catName=menuUploadFile2.getCategory();
							System.out.println("catName.."+catName);
							try {
								catName=catName.trim();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							
							List<MenuCategory> catList=null;
							
							//check if already category exists
							TypedQuery<MenuCategory> qryCat = em
									.createQuery("SELECT m FROM MenuCategory m WHERE m.menuCategoryName=:menuCategoryName and m.storeId=:storeId", MenuCategory.class);
							qryCat.setParameter("menuCategoryName", catName);
							qryCat.setParameter("storeId", storeId);
							catList = qryCat.getResultList();
							if (catList.size()>0) {
								for (Iterator<MenuCategory> iterator2 = catList.iterator(); iterator2.hasNext();) {
									category = (MenuCategory) iterator2.next();
								}
							}
							else{
								//if not exists category
								try {
									category = new MenuCategory();
									category.setMenuCategoryName(catName.trim());
									category.setMenutype(root);
									category.setStoreId(storeId);
									category.setDeleteFlag("N");
									category.setType("c");
									category.setBgColor("ffffff");
									category.setDirectCat("N");
									category.setPrintStatus("N");
									em.persist(category);
								} catch (Exception e) {
									menuUploadFile2.setError("category "+menuUploadFile2.getCategory()+" not inserted.");
									errorList.add(menuUploadFile2);
									//e.printStackTrace();
								}
						}
							
							if(category.getId()>0){
								String subCateName=menuUploadFile2.getSubCategory();
								//MenuCategory subCat;
								List<MenuCategory> subCatList;
								MenuCategory subcategory=null;
								
								
									//check if already sub category exists
									TypedQuery<MenuCategory> qrySubCat = em
											.createQuery("SELECT m FROM MenuCategory m WHERE m.menuCategoryName=:menuCategoryName and m.storeId=:storeId", MenuCategory.class);
									qrySubCat.setParameter("menuCategoryName", subCateName.trim());
									qrySubCat.setParameter("storeId", storeId);
									subCatList = qrySubCat.getResultList();
									
									if (subCatList.size()>0) {
										for (Iterator<MenuCategory> iterator2 = subCatList.iterator(); iterator2.hasNext();) {
											subcategory = (MenuCategory) iterator2.next();
										}
									}
									
									//if not exists sub category
									else{
										
										try {
										
											subcategory =new MenuCategory();
											subcategory.setMenuCategoryName(subCateName.trim());
											subcategory.setMenutype(category);
											subcategory.setStoreId(storeId);
											subcategory.setDeleteFlag("N");
											subcategory.setType("s");
											subcategory.setBgColor("ffffff");
											subcategory.setDirectCat("N");
											subcategory.setPrintStatus("N");
											em.persist(subcategory);
										} catch (Exception e) {
											menuUploadFile2.setError("sub category "+menuUploadFile2.getSubCategory()+" not inserted.");
											errorList.add(menuUploadFile2);
											// TODO Auto-generated catch block
											//e.printStackTrace();
										}
								}
									
								if(subcategory.getId()>0){
									//get sub category id
									int subCateId=subcategory.getId();
									String itemName=menuUploadFile2.getMenuItem();
									MenuItem item=null;
									List<MenuItem> itemList=null;
									
									
										TypedQuery<MenuItem> qryItem = em
												.createQuery("SELECT i FROM MenuItem i WHERE i.menucategory.id=:subCateId and i.name=:name and i.storeId=:storeId", MenuItem.class);
										qryItem.setParameter("subCateId", subCateId);
										qryItem.setParameter("name", itemName);
										qryItem.setParameter("storeId", storeId);
										itemList =qryItem.getResultList();
										
										if (itemList.size()>0) {
											for (Iterator<MenuItem> iterator2 = itemList.iterator(); iterator2.hasNext();) {
												item = (MenuItem) iterator2.next();

											}
											System.out.println("item found---> "+item.getName());
										}
										
										else{
										//insert item for the sub category
											try {
												item=new MenuItem();
												item.setName(menuUploadFile2.getMenuItem());
												item.setDescription(menuUploadFile2.getMenuItem());
												item.setPrice(menuUploadFile2.getRate());
												item.setMenucategory(subcategory);
												item.setVeg(menuUploadFile2.getVeg());
												item.setStoreId(storeId);
												item.setDeleteFlag("N");
												item.setProduction("Y");
												item.setUnit("plate");
												em.persist(item);
											} catch (Exception e) {
												// TODO Auto-generated catch block
												menuUploadFile2.setError("item "+menuUploadFile2.getMenuItem()+" not inserted.");
												errorList.add(menuUploadFile2);
												e.printStackTrace();
											}
									}
									
							}	
									
						}
					
				}
			em.getTransaction().commit();
		  //  file.close();
		    status="Success";
		    

		}
		catch (IllegalStateException e) {
			//throw new FileUplodException(menuUploadFile, "");
			   status="failure";
            e.printStackTrace();
        }
		catch (FileNotFoundException e) {
			 status="failure";
			e.printStackTrace();
		} catch (IOException e) {
			 status="failure";
			e.printStackTrace();
		} catch (Exception e) {
			 status="failure";
			 e.printStackTrace();
			//throw new FileUplodException(menuUploadFile, "");
			// TODO: handle exception
		}
		
		finally{
			em.close();
		}
		return status;
	}
	
	

}
