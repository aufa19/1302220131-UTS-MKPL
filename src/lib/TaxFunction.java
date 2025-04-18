package lib;

public class TaxFunction {

	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus
	 * dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan
	 * bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan)
	 * dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena
	 * pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah
	 * sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya
	 * ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 * 
	 */

	// Konstanta untuk PTKP
	private static final int BASE_PTKP = 54000000; // Penghasilan Tidak Kena Pajak untuk single
	private static final int MARRIED_PTKP_ADJUSTMENT = 4500000; // Tambahan PTKP jika menikah
	private static final int CHILD_PTKP_ADJUSTMENT = 1500000; // Tambahan PTKP per anak

	public static int calculateTax(EmployeeTaxData taxData) {
		int tax = 0;

		// Validasi jumlah bulan bekerja
		if (taxData.getNumberOfMonthWorking() > 12) {
			System.err.println("More than 12 months working per year");
			return 0;
		}

		// Hitung penghasilan bersih tahunan
		int annualIncome = (taxData.getMonthlySalary() + taxData.getOtherMonthlyIncome())
				* taxData.getNumberOfMonthWorking();
		int taxableIncome = annualIncome - taxData.getDeductible();

		// Hitung PTKP
		int ptkp = BASE_PTKP;
		if (taxData.isMarried()) {
			ptkp += MARRIED_PTKP_ADJUSTMENT;
		}
		ptkp += taxData.getNumberOfChildren() * CHILD_PTKP_ADJUSTMENT;

		// Hitung pajak
		taxableIncome -= ptkp;
		if (taxableIncome > 0) {
			tax = (int) Math.round(0.05 * taxableIncome);
		}

		return tax;
	}
}