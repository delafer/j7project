package de.creditreform.common.xml.model;

import java.io.Serializable;

public interface IEntry extends Serializable {

	public enum Type {XmlTag, XmlText};


	public final static String ATTR_PREFFIX = "#";

	public boolean isAttribute();

//	public enum DocumentType {
//		ReportResponse("ns2:reportResponse"), MainBoxEntryResponse("ns2:mailboxentryResponse"), Lieferung("Lieferung"), Unknown("Unknown"); // unknown document type
//
//		DocumentType(String id) {tag = id;} public String tag;
//	}


//	public static class MetaTag {
//
//
//
//		public MetaTag(String name) {
//			this.name = name;
//		}
//
//
//
//	}


//	public enum MetaTag {
//		DocumentType, Name1, Name2, Name3, Street, StreetWithNr, HomeNr, AddressExtension, City, CountryCode, ZIP, CrefoNr, Boni, Boni2, CreationDateTime, CreationDate, CreationTime,
//		LegalformPrimary, LegalFormSecondary, TextAuskunft, FoundationDate, InquirySendDate, InquireySendTime, InquirySendTimestamp, AddendumDeadline, InquiryReferenceNr, PaymentForm,
//		CreditJudgment, AnnualTurnover , AT_Currency, AnnualTurnoverSign, TotalAssets, TotalAssetsSign, TA_Currency, EmployeesCount, UserIdIns,
//		Companyname,
//		Tradename,
//		Commercialname,
//		Quarter,
//		DateOfBirth,
//		DiallingCode,
//		Phonenumber,
//		ExtensionNo,
//		Email,
//		Website,
//		Birthname,
//		Nameaffix,
//		SurnameWidow,
//		SurnameBeforeDivorce,
//		Alias,
//		Requestdata,
//		Taxdata,
//		DateOfFoundation,
//		DateOfFirstLegalForm,
//		DateLegalForm,
//		RegisterId,
//
//		StatusReplyTxt,//new
//		FoundationTxt,//new
//		RegisterTxt,//new
//
//		Sh_CrefoNr,
//		Sh_Name1,
//		Sh_Name2,
//		Sh_Birthname,
//		Sh_DateOfBirth,
//		Sh_Companyname,
//		Sh_Street,
//		Sh_HomeNr,
//		Sh_AddressExtension,
//		Sh_Zip,
//		Sh_City,
//		Sh_Quarter,
//		Sh_ComplCapShareHldr,
//		Sh_Other,
//		Sh_Former,
//
//
//		DeputyManagement,
//		Pc_CrefoNr,
//		Pc_Companyname,
//		Pc_Tradename,
//		Pc_Commercialname,
//		Pc_Alias,
//		Pc_Street,
//		Pc_HomeNr,
//		Pc_AddressExtension,
//		Pc_Zip,
//		Pc_City,
//		Pc_Formerparticipations,
//		ParticipationsPrivatePerson,
//		ParticipationsParticipants,
//		Rd_Banks,
//		Rd_RealeStates,
//		Rd_LegalDisclaimer,
//
//		ParticipationsCompanyTxt,//new
//		BusinessPurpose,//new
//		BranchTxt,//new
//		StaffCompanyTxt,//new
//		StaffGroupTxt,//new
//		StaffGroupCompanyname,//new
//		TurnoverCompanyTxt,//new
//		TurnoverGroupTxt,//new
//		BalanceSheetTxt,//new
//		AssetHeader,//new
//		LiabilitiesHeader,//new
//		Remarks,//new
//		BusinessDevelopmentTxt,//new
//		UpdateNote,//new
//		SpecificationOfProfession,//new
//		AncillaryInformationTxt,//new
//		FinancialData,//new
//		BusinessArea,//new
//		ImportExport,//new
//		PaymentModeTxt,//new
//		CreditOpinionTxt,//new
//		Supplement,//new
//		NegativeFact,//new
//		Locations,//new
//		FinancialStatusPrivatePerson,//new
//		FormerLocations,//new
//		AffiliatedGroup,//new
//		BalanceSheetGroup,//new
//		ProfitslossesGroup,//new
//		EvaluationsFinanceIndustry,//new
//		BalanceSolvency,//new
//		FinancialStatementFigures,//new
//		FinancialStatementFiguresGroup,//new
//		TurnoverCompanyRange,//new
//		StaffCompanyRange,//new
//		PaymentBehaviour,//new
//		FinancialInformation,//new
//		ReportHints,//new
//		Sh_ShareholderCapitalTxt,//new
//		Sh_CapacitiesShareholderActiveTxt,//new
//		Sh_CapacitiesShareholderTxt,//new
//
//	};


	public Type getType();

	public CharSequence render();


	public boolean isTagXml();

	public boolean isTagTxt();

	public boolean isTagStructure();

}
