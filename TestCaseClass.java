package com.capgemini.wallet.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.capgemini.wallet.beans.Customer;
import com.capgemini.wallet.beans.Wallet;
import com.capgemini.wallet.exception.InsufficientBalanceException;
import com.capgemini.wallet.exception.InvalidInputException;
import com.capgemini.wallet.repo.WalletRepoImpl;
import com.capgemini.wallet.service.WalletService;
import com.capgemini.wallet.service.WalletServiceImpl;

public class TestCaseClass {

	
	
	WalletService service;
	WalletRepoImpl repo;
	Customer customer1,customer2,customer3;
	
	
	@Before
	public void initialiseData(){
		Map<String, Customer> data = new HashMap<String,Customer>();
		customer1 = new Customer("Steve", "1234567890", new Wallet(new BigDecimal(9000)));
		customer2 = new Customer("Jobs","0987654321", new Wallet(new BigDecimal(5000)));
		customer3 = new Customer("Harry","8983105678", new Wallet(new BigDecimal(7500)));
		
		data.put("1234567890", customer1);
		data.put("0987654321", customer2);
		data.put("8983105678", customer3);
		service = new WalletServiceImpl(data);
		
	}
	

	@Test
	public void testPhoneInput() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.createAccount("Steve", "9850871989", new BigDecimal(20000));
		assertEquals("9850871989", customer.getMobileNo());
	}
	
	
	@Test
	public void testNameInput() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.createAccount("Steve", "9850871989", new BigDecimal(20000));
		assertEquals("Steve", customer.getName());
	}
	
	@Test
	public void testAmountInput() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.createAccount("Steve", "9850871989", new BigDecimal(20000));
		assertEquals( new BigDecimal(20000), customer.getWallet().getBalance());
	}
	
	@Test
	public void testShowBalance1() throws InvalidInputException
	{
		Customer customer = new Customer();
		customer = service.showBalance("1234567890");
		assertEquals(new BigDecimal(9000), customer.getWallet().getBalance());
	}
	
	
	@Test
	public void testShowBalance2() throws InvalidInputException
	{
		Customer customer = new Customer();
		customer = service.showBalance("0987654321");
		assertEquals(new BigDecimal(5000), customer.getWallet().getBalance());
	}
	
	
	@Test
	public void testShowBalance3() throws InvalidInputException
	{
		Customer customer = new Customer();
		customer = service.showBalance("8983105678");
		assertEquals(new BigDecimal(7500), customer.getWallet().getBalance());
	}
	
	
	
	@Test(expected = InsufficientBalanceException.class)
	public void testWithdraw1() throws InvalidInputException, InsufficientBalanceException
	{
		Customer customer = new Customer();
		customer = service.withdrawAmount("8983105678", new BigDecimal(100000));
		
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void testWithdraw2() throws InvalidInputException, InsufficientBalanceException
	{
		Customer customer = new Customer();
		customer = service.withdrawAmount("0987654321", new BigDecimal(66000));
		
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void testWithdraw3() throws InvalidInputException, InsufficientBalanceException
	{
		Customer customer = new Customer();
		customer = service.withdrawAmount("1234567890", new BigDecimal(9001));
		
	}
	
	@Test
	public void testValidation1() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.createAccount("Jane", "1234567890", new BigDecimal(1100));
		assertEquals(10,customer.getMobileNo().length());
	}
	
	@Test
	public void testValidation2() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.createAccount("Jane", "9850871989", new BigDecimal(1100));
		assertEquals(10,customer.getMobileNo().length());
	}
	
	@Test
	public void testDepositAmount1() throws InvalidInputException {
		Customer customer = new Customer();
		customer=service.depositAmount("1234567890", new BigDecimal(500));
		assertEquals(new BigDecimal(9500), customer.getWallet().getBalance());
	}
	
	@Test
	public void testDepositAmount2() throws InvalidInputException {
		Customer customer = new Customer();
		customer=service.depositAmount("0987654321", new BigDecimal(500));
		assertEquals(new BigDecimal(5500), customer.getWallet().getBalance());
	}
	
	@Test
	public void testDepositAmount3() throws InvalidInputException {
		Customer customer = new Customer();
		customer=service.depositAmount("8983105678", new BigDecimal(500));
		assertEquals(new BigDecimal(8000), customer.getWallet().getBalance());
	}
	
	
	@Test
	public void testfundTransfer1() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.fundTransfer("1234567890", "0987654321", new BigDecimal(500));
		assertEquals(new BigDecimal(8500), customer.getWallet().getBalance());
	}
	
	
	@Test
	public void testfundTransfer2() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.fundTransfer("1234567890", "0987654321", new BigDecimal(500));
		assertEquals(new BigDecimal(8500), customer.getWallet().getBalance());
	}
	
	
	@Test
	public void testfundTransfer3() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.fundTransfer("8983105678", "0987654321", new BigDecimal(500));
		assertEquals(new BigDecimal(7000), customer.getWallet().getBalance());
	}
	
	@Test(expected = InvalidInputException.class )
	public void testfundTransfer4() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.fundTransfer("8983105678", "0987654321", new BigDecimal(8000));
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testfindOneRepo() throws InvalidInputException {
		Customer customer = new Customer("","",new Wallet(null));
		customer = repo.findOne(customer.getMobileNo());
		
	}
	
	@Test
	public void testIsValid() throws InvalidInputException {
		Customer customer = new Customer("Akash","7387678820",new Wallet(new BigDecimal(150000)));
		assertTrue(service.isValid(customer));
	}
	
	@Test
	public void testIsValid2() throws InvalidInputException {
		Customer customer = new Customer("Harry","8983105678",new Wallet(new BigDecimal(5000)));
		assertTrue(service.isValid(customer));
	}
}

























