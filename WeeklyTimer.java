package chocAn;

/*
 * runs the main accounting procedure.
 * @author Susan Xiao
 */

public class WeeklyTimer {

	public boolean runMainAccountProcedure(MemberRecords[] memArr, ProviderRecords[] proArr, int memSize, int proSize, ServiceRecord[] service, int servSize) {
		ReportGenerator reportGenerator = new ReportGenerator();

		/*
		 * generate member reports
		 */
		for (int i=0; i<memSize; i++) {
			if (memArr[i] != null) {
				reportGenerator.generateMemberReport(memArr, proArr, memSize, proSize, service, servSize, memArr[i].memberRecord.getNumID()); //memArr[i]
			}
		}
		System.out.println("member reports generated.");

		/*
		 * generate provider reports
		 */
		for (int i=0; i<proSize; i++) {
			if (proArr[i] != null) {
				reportGenerator.generateProviderReport(memArr, proArr, memSize, proSize, service, servSize, proArr[i].providerRecord.getNumID()); //proArr[i]
			}
		}
		System.out.println("provider reports generated.");

		/*
		 * generate summary report
		 */
		reportGenerator.generateSummaryReport(memArr, proArr, memSize, proSize, service, servSize);
		System.out.println("Summary reports generated.");

		/*
		 * generate EFT
		 */
		reportGenerator.generateEFTReport(memArr, proArr, memSize, proSize, service, servSize);
		System.out.println("EFT generated.");

		System.out.println("reports generated.");

		return true;
	}
}
