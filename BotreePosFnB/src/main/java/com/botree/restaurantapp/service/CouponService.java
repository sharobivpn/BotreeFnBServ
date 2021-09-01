package com.botree.restaurantapp.service;

import java.util.List;

import com.botree.restaurantapp.dao.CouponsDAO;
import com.botree.restaurantapp.dao.exception.DAOException;
import com.botree.restaurantapp.model.Coupon;
import com.botree.restaurantapp.model.Customer;
import com.botree.restaurantapp.service.exception.ServiceException;

public class CouponService {
	private CouponsDAO couponsDAO = null;
	private Coupon coupon2 = null;

	public CouponService() {
		
	}

	public void createCoupon(String coupon) throws ServiceException {
		try {
			System.out.println("Enter CouponService.createCoupon ");
			// create a new coupon
			couponsDAO.createCoupon(coupon);
			System.out.println("exit CouponService.createCoupon ");

		} catch (DAOException e) {
			throw new ServiceException("create coupon error", e);

		}

	}

	public List<Coupon> getCoupon() throws ServiceException {
		List<Coupon> coupList = null;
		try {
			System.out.println("Enter CouponService.getCoupon ");
			// get coupon
			coupList = couponsDAO.getCoupons();
			System.out.println("exit CouponService.getCoupon ");

		} catch (DAOException e) {
			throw new ServiceException("get coupon error", e);

		}

		return coupList;
	}

	public void deleteCoupon(Coupon coupon) throws ServiceException {
		try {
			System.out.println("Enter CouponService.deleteCoupon ");
			// delete coupon
			couponsDAO.deleteCoupon(coupon);
			System.out.println("exit CouponService.deleteCoupon ");

		} catch (DAOException e) {
			throw new ServiceException("delete coupon error", e);

		}

	}

	public String sendCoupon(List<Customer> customer, String coupon) throws ServiceException{
		try {
			System.out.println("Enter CouponService.sendCoupon ");
			coupon2.setCouponValue(coupon);
			//coupon2.setCustomer(customer);
			coupon2.setOffer(null);
			// send coupon
			couponsDAO.sendCoupon(coupon2);
			System.out.println("exit CouponService.sendCoupon ");

		} catch (DAOException e) {
			throw new ServiceException("send coupon error", e);

		}

		return coupon;
	}
	
	/*public byte[] sendCoupon(List<Customer> customer, byte[] coupon) {
		try {
			System.out.println("Enter CouponService.sendCoupon ");
			coupon2.setCoupon_value(coupon);
			coupon2.setCustomer(customer);
			coupon2.setOffer(null);
			// send coupon
			couponsDAO.sendCoupon(coupon2);
			System.out.println("exit CouponService.sendCoupon ");

		} catch (Exception e) {

		}

		return coupon;
	}*/

}
