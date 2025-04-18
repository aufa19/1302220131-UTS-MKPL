package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	public enum Gender {
		pria, wanita;
	}

	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;

	private int yearJoined;
	private int monthJoined;
	private int dayJoined;

	private boolean isForeigner;
	private Gender gender;

	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;

	private String spouseName;
	private String spouseIdNumber;

	private List<String> childNames;
	private List<String> childIdNumbers;

	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address,
			int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, Gender gender) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.address = address;
		this.yearJoined = yearJoined;
		this.monthJoined = monthJoined;
		this.isForeigner = isForeigner;
		this.gender = gender;

		childNames = new LinkedList<>();
		childIdNumbers = new LinkedList<>();
	}

	private static final int GRADE_1_SALARY = 3000000;
	private static final int GRADE_2_SALARY = 5000000;
	private static final int GRADE_3_SALARY = 7000000;

	private int calculateForeignerSalary(int baseSalary) {
		return isForeigner ? (int) (baseSalary * 1.5) : baseSalary;
	}

	public void setMonthlySalary(int grade) {
		switch (grade) {
			case 1:
				monthlySalary = calculateForeignerSalary(GRADE_1_SALARY);
				break;
			case 2:
				monthlySalary = calculateForeignerSalary(GRADE_2_SALARY);
				break;
			case 3:
				monthlySalary = calculateForeignerSalary(GRADE_3_SALARY);
				break;
			default:
				throw new IllegalArgumentException("Invalid grade. Grade semestinya berada antara 1 dan 3.");
		}
	}

	public void setAnnualDeductible(int deductible) {
		this.annualDeductible = deductible;
	}

	public void setAdditionalIncome(int income) {
		this.otherMonthlyIncome = income;
	}

	public void setSpouse(String spouseName, String spouseIdNumber) {
		this.spouseName = spouseName;
		this.spouseIdNumber = spouseIdNumber;
	}

	public void addChild(String childName, String childIdNumber) {
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
	}

	private int calculateMonthWorkingInYear(LocalDate date) {
		if (date.getYear() == yearJoined) {
			return date.getMonthValue() - monthJoined;
		} else {
			return 12;
		}
	}

	public int getAnnualIncomeTax() {
		LocalDate date = LocalDate.now();
		int monthWorkingInYear = calculateMonthWorkingInYear(date);

		EmployeeTaxData taxData = new EmployeeTaxData(
				monthlySalary,
				otherMonthlyIncome,
				monthWorkingInYear,
				annualDeductible,
				spouseIdNumber != null && !spouseIdNumber.isEmpty(),
				childIdNumbers.size());

		return TaxFunction.calculateTax(taxData);
	}
}