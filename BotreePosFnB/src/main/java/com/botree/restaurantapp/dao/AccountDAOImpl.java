/**
 * 
 */
package com.botree.restaurantapp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.DailyExpenditure;
import com.botree.restaurantapp.model.DailyExpenditureType;
import com.botree.restaurantapp.model.util.PersistenceListener;

/**
 * @author admin
 *
 */
@Component("accountDAO")
public class AccountDAOImpl implements AccountDAO {
	
	@Override
	public int addDailyExpenditure(DailyExpenditure dailyExpenditure)
			throws DAOException {
		EntityManager em = null;
		int dailyExpId;
		try {

			dailyExpId = 0;
			// Date currDate = new Date();
			// dailyExpenditure.setVoucherDate(currDate);

			// SimpleDateFormat

			// persist the daily expenditure
			dailyExpenditure.setDeleteFlag("N");

			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();
			em.persist(dailyExpenditure);

			em.getTransaction().commit();
			dailyExpId = dailyExpenditure.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return dailyExpId;
	}
	
	@Override
	public List<DailyExpenditure> getDailyExpenditureByDate(String date,
			String storeId) throws DAOException {

		List<DailyExpenditure> dailyExpenditureLst = new ArrayList<DailyExpenditure>();
		EntityManager em = null;
		//SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

    Map<Integer, DailyExpenditureType> dailyExpenditureTypeMap = new HashMap<>();
    List<DailyExpenditureType> dailyExpenditureTypes = getExpenditureTypes(); //.stream().collect(Collectors.toMap(DailyExpenditureType::getId, DailyExpenditureType));
    
    for(DailyExpenditureType dailyExpenditureType : dailyExpenditureTypes) {
      dailyExpenditureTypeMap.put(dailyExpenditureType.getId(), dailyExpenditureType);
    }
    
		Date voucherDate = null;

		try {
			// String voucherDateStrng = FORMAT.format(date);
			voucherDate = FORMAT.parse(date);
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();
			int storeid = Integer.parseInt(storeId);

			TypedQuery<DailyExpenditure> qry = em
          .createQuery("SELECT d FROM DailyExpenditure d WHERE d.voucherDate=:voucherDate and d.storeId=:storeId and d.deleteFlag='N'", DailyExpenditure.class);
			qry.setParameter("voucherDate", voucherDate);
			qry.setParameter("storeId", storeid);
			dailyExpenditureLst = qry.getResultList();
			
			//added on 16.05.2018
			Iterator<DailyExpenditure> iterator = dailyExpenditureLst
					.iterator();
			while (iterator.hasNext()) {
				DailyExpenditure dailyexp = (DailyExpenditure) iterator
						.next();
				
				int exptypeId = dailyexp.getExpenditureTypeId();
				/*
				if(exptypeId>0)
				{
				Query qryGetExpType = em
						.createQuery("SELECT et FROM DailyExpenditureType et WHERE et.id=:expTypeId");
				qryGetExpType.setParameter("expTypeId", exptypeId);
				DailyExpenditureType expType = (DailyExpenditureType) qryGetExpType
						.getSingleResult();
				dailexp.setExpenditureTypeName(expType.getTypeName());
				}
				else
				{
					dailexp.setExpenditureTypeName("NA");
				}
				*/
        dailyexp.setExpenditureTypeName(exptypeId > 0 ? dailyExpenditureTypeMap.get(exptypeId).getTypeName() : "NA");

			}


			em.getTransaction().commit();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			em.close();
		}
		return dailyExpenditureLst;
	}
	
	@Override
	public List<DailyExpenditure> getDailyExpenditureByPeriod(String date,
			String toDate, String storeId) throws DAOException {

		List<DailyExpenditure> dailyExpenditureLst = new ArrayList<DailyExpenditure>();
		EntityManager em = null;

		Date voucherDate = null;
		Date voucherDate2 = null;

    Map<Integer, DailyExpenditureType> dailyExpenditureTypeMap = new HashMap<>();
		List<DailyExpenditureType> dailyExpenditureTypes = getExpenditureTypes(); //.stream().collect(Collectors.toMap(DailyExpenditureType::getId, DailyExpenditureType));
		
		for(DailyExpenditureType dailyExpenditureType : dailyExpenditureTypes) {
		  dailyExpenditureTypeMap.put(dailyExpenditureType.getId(), dailyExpenditureType);
		}
		
		try {
			voucherDate = FORMAT.parse(date);
			voucherDate2 = FORMAT.parse(toDate);
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();
			int storeid = Integer.parseInt(storeId);
			TypedQuery<DailyExpenditure> qry = em
          .createQuery("SELECT d FROM DailyExpenditure d WHERE d.voucherDate >=:voucherDate and d.voucherDate <=:voucherDate2 and d.storeId=:storeId and d.deleteFlag='N'", DailyExpenditure.class);
			qry.setParameter("voucherDate", voucherDate);
			qry.setParameter("voucherDate2", voucherDate2);
			qry.setParameter("storeId", storeid);
			dailyExpenditureLst = qry.getResultList();
			
			//added on 16.05.2018
			Iterator<DailyExpenditure> iterator = dailyExpenditureLst
					.iterator();
			while (iterator.hasNext()) {
				DailyExpenditure dailyexp = (DailyExpenditure) iterator
						.next();
				
				int exptypeId = dailyexp.getExpenditureTypeId();
				/*
				if(exptypeId>0)
				{
				Query qryGetExpType = em
						.createQuery("SELECT et FROM DailyExpenditureType et WHERE et.id=:expTypeId");
				qryGetExpType.setParameter("expTypeId", exptypeId);
				DailyExpenditureType expType = (DailyExpenditureType) qryGetExpType
						.getSingleResult();
				dailexp.setExpenditureTypeName(expType.getTypeName());
				}
				else
				{
					dailexp.setExpenditureTypeName("NA");
				}
				*/
        dailyexp.setExpenditureTypeName(exptypeId > 0 ? dailyExpenditureTypeMap.get(exptypeId).getTypeName() : "NA");

			}

			
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);
		} finally {
			em.close();
		}
		return dailyExpenditureLst;
	}
	
	@Override
	public String deleteDailyExpenditure(DailyExpenditure dailyExpenditure) throws DAOException {
		EntityManager em = null;
		String status = "";
		
		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();
			DailyExpenditure dailyExpenditure1 = em.find(DailyExpenditure.class, dailyExpenditure.getId());
			dailyExpenditure1.setDeleteFlag("Y");
			
			em.getTransaction().commit();
			status = "success";
			System.out.print("daily exp deleted successfully....");
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check daily exp to be deleted", e);
		} finally {
			em.close();
		}

		return status;
	}
	
	@Override
	public String updateDailyExpenditure(DailyExpenditure dailyExpenditure) throws DAOException {
		EntityManager em = null;
		String status = "";
		
		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();
			DailyExpenditure dailyExpenditure1 = em.find(DailyExpenditure.class, dailyExpenditure.getId());
			dailyExpenditure1.setExpenditureTypeId(dailyExpenditure.getExpenditureTypeId());
			dailyExpenditure1.setVoucherId(dailyExpenditure.getVoucherId());
			dailyExpenditure1.setVendorName(dailyExpenditure.getVendorName());
			dailyExpenditure1.setParticulars(dailyExpenditure.getParticulars());
			dailyExpenditure1.setAmount(dailyExpenditure.getAmount());
			dailyExpenditure1.setTaxRate(dailyExpenditure.getTaxRate());
			dailyExpenditure1.setTaxAmt(dailyExpenditure.getTaxAmt());
			dailyExpenditure1.setNetAmt(dailyExpenditure.getNetAmt());
			dailyExpenditure1.setVoucherDate(dailyExpenditure.getVoucherDate());
			dailyExpenditure1.setUpdatedBy(dailyExpenditure.getUpdatedBy());
			dailyExpenditure1.setUpdatedDate(dailyExpenditure.getUpdatedDate());
			em.merge(dailyExpenditure1);
			
			em.getTransaction().commit();
			status = "success";
			System.out.print("daily exp updated successfully....");
		} catch (Exception e) {
			e.printStackTrace();
			status = "failure";
			throw new DAOException("Check daily exp to be updated", e);
		} finally {
			em.close();
		}

		return status;
	}
	
	//added on 15.05.2018
	@Override
	public List<DailyExpenditureType> getExpenditureTypes() throws DAOException {

	  List<DailyExpenditureType> dailyExpenditureTypeLst = new ArrayList<DailyExpenditureType>();
		EntityManager em = null;

		try {
//			entityManagerFactory = PersistenceListener.getEntityManager();
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();

			TypedQuery<DailyExpenditureType> qry = em
          .createQuery("SELECT d FROM DailyExpenditureType d WHERE d.activeFlag='Y'", DailyExpenditureType.class);
			dailyExpenditureTypeLst = qry.getResultList();

			em.getTransaction().commit();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			em.close();
		}
		return dailyExpenditureTypeLst;
	}

}
