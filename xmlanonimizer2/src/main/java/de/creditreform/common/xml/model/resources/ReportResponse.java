package de.creditreform.common.xml.model.resources;

import java.util.Map;

import de.creditreform.common.helpers.StringUtils;
import de.creditreform.common.xml.model.IEntry.DocumentType;
import de.creditreform.common.xml.model.MetaTag;

public class ReportResponse extends AnonimizeSpec {

	public ReportResponse(Map<String, MultiValue<String>> data) {
		super(data);
	}

	public ReportResponse() {
		super();
	}

	@Override
	public DocumentType getDocumentType() {
		return DocumentType.ReportResponse; //ReportResponse("ns2:reportResponse")
	}

	@Override
	public TagData[] getRelevantTags() {
		return new TagData[] {

		        //Common info
		        TagData.as("ns2:", "reportResponse/body/identificationnumber", MetaTag.valueOf("CrefoNr")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/solvencyindex/solvencyindexone", MetaTag.valueOf("Boni")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/solvencyindex/solvencyindextwozero", MetaTag.valueOf("Boni2")),
		        TagData.as("ns2:", "reportResponse/body/creationtime", MetaTag.valueOf("CreationDateTime")),
		        TagData.as("ns2:", "reportResponse/body/reportdata", MetaTag.valueOf("TextAuskunft")),
		        //additional fields
		        TagData.as("ns2:", "reportResponse/header/transmissiontimestamp", MetaTag.valueOf("InquirySendTimestamp")),
		        TagData.as("ns2:", "reportResponse/header/useraccount", MetaTag.valueOf("UserIdIns")),
		        TagData.as("ns2:", "reportResponse/body/endofstandardmonitoring", MetaTag.valueOf("AddendumDeadline")),
		        TagData.as("ns2:", "reportResponse/body/referencenumber", MetaTag.valueOf("InquiryReferenceNr")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/paymentmode/paymentmode/key", MetaTag.valueOf("PaymentForm")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/creditopinion/typeofcreditopinion/designation", MetaTag.valueOf("CreditJudgment")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/turnovercompany/fiscalyear/turnoverlist/amount/value", MetaTag.valueOf("AnnualTurnover")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/turnovercompany/fiscalyear/turnoverlist/amount/currency/key", MetaTag.valueOf("AT_Currency")),//Random
		        TagData.as("ns2:", "reportResponse/body/reportdata/balancesheet/balance/assets/sumassets/amount", MetaTag.valueOf("TotalAssets")),
//						TagData.as("Lieferung/Auskunft/StammCrefo/Finanzinformationen/WaehrungPositionsBetrag", MetaTag.valueOf("TA_Currency")), //always EUR


		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/companyname", MetaTag.valueOf("Companyname")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/street", MetaTag.valueOf("Street")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/postcode", MetaTag.valueOf("ZIP")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/city", MetaTag.valueOf("City")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/country/designation", MetaTag.valueOf("CountryCode")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification", MetaTag.valueOf("LegalformPrimary")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/staffcompany/fiscalyear/totalstaff", MetaTag.valueOf("EmployeesCount")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/tradename", MetaTag.valueOf("Tradename")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/commercialname", MetaTag.valueOf("Commercialname")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/housenumber", MetaTag.valueOf("HomeNr")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/housenumberaffix", MetaTag.valueOf("AddressExtension")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/quarter", MetaTag.valueOf("Quarter")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/phone/diallingcode", MetaTag.valueOf("DiallingCode")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/phone/phonenumber", MetaTag.valueOf("Phonenumber")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/phone/extensionno", MetaTag.valueOf("ExtensionNo")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/fax/diallingcode", MetaTag.valueOf("DiallingCode")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/fax/phonenumber", MetaTag.valueOf("Phonenumber")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/fax/extensionno", MetaTag.valueOf("ExtensionNo")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/mobile/diallingcode", MetaTag.valueOf("DiallingCode")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/mobile/phonenumber", MetaTag.valueOf("Phonenumber")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/mobile/extensionno", MetaTag.valueOf("ExtensionNo")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/email", MetaTag.valueOf("Email")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/companyidentification/website", MetaTag.valueOf("Website")),


		    	TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/firstname", MetaTag.valueOf("Name1")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/surname", MetaTag.valueOf("Name2")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/street", MetaTag.valueOf("Street")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/housenumber", MetaTag.valueOf("HomeNr")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/housenumberaffix", MetaTag.valueOf("AddressExtension")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/postcode", MetaTag.valueOf("ZIP")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/city", MetaTag.valueOf("City")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/country", MetaTag.valueOf("CountryCode")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification", MetaTag.valueOf("LegalformPrimary")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/birthname", MetaTag.valueOf("Birthname")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/nameaffix", MetaTag.valueOf("Nameaffix")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/surnamewidow", MetaTag.valueOf("SurnameWidow")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/surnamebeforedivorce", MetaTag.valueOf("SurnameBeforeDivorce")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/alias", MetaTag.valueOf("Alias")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/quarter", MetaTag.valueOf("Quarter")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/dateofbirth", MetaTag.valueOf("DateOfBirth")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/phone/diallingcode", MetaTag.valueOf("DiallingCode")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/phone/phonenumber", MetaTag.valueOf("Phonenumber")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/phone/extensionno", MetaTag.valueOf("ExtensionNo")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/fax/diallingcode", MetaTag.valueOf("DiallingCode")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/fax/phonenumber", MetaTag.valueOf("Phonenumber")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/fax/extensionno", MetaTag.valueOf("ExtensionNo")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/mobile/diallingcode", MetaTag.valueOf("DiallingCode")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/mobile/phonenumber", MetaTag.valueOf("Phonenumber")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/mobile/extensionno", MetaTag.valueOf("ExtensionNo")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/email", MetaTag.valueOf("Email")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/privatepersonidentification/website", MetaTag.valueOf("Website")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/requestdata", MetaTag.valueOf("Requestdata")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/taxdata", MetaTag.valueOf("Taxdata")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/foundation/dateoffoundation", MetaTag.valueOf("DateOfFoundation")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/foundation/dateoffirstlegalform", MetaTag.valueOf("DateOfFirstLegalForm")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/register/datelegalform", MetaTag.valueOf("DateLegalForm")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/register/registerid", MetaTag.valueOf("RegisterId")),


		        TagData.as("ns2:", "reportResponse/body/reportdata/participationsprivateperson", MetaTag.valueOf("ParticipationsPrivatePerson")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/participationsparticipants", MetaTag.valueOf("ParticipationsParticipants")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/banks", MetaTag.valueOf("Rd_Banks")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/realestates", MetaTag.valueOf("Rd_RealeStates")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/legaldisclaimer", MetaTag.valueOf("Rd_LegalDisclaimer")),


		        TagData.as("ns2:", "reportResponse/body/reportdata/deputymanagement", MetaTag.valueOf("DeputyManagement")),


		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/active/identificationnumber", MetaTag.valueOf("Sh_CrefoNr")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/active/privateperson/surname", MetaTag.valueOf("Sh_Name2")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/active/privateperson/firstname", MetaTag.valueOf("Sh_Name1")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/active/privateperson/birthname", MetaTag.valueOf("Sh_Birthname")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/active/privateperson/dateofbirth", MetaTag.valueOf("Sh_DateOfBirth")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/active/company/companyname", MetaTag.valueOf("Companyname")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/active/street", MetaTag.valueOf("Sh_Street")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/active/housenumber", MetaTag.valueOf("Sh_HomeNr")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/active/housenumberaffix", MetaTag.valueOf("Sh_AddressExtension")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/active/postcode", MetaTag.valueOf("Sh_Zip")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/active/city", MetaTag.valueOf("Sh_City")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/active/quarter", MetaTag.valueOf("Sh_Quarter")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/active/complementarycapacitiesshareholder", MetaTag.valueOf("Sh_ComplCapShareHldr")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/other", MetaTag.valueOf("Sh_Other")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/former", MetaTag.valueOf("Sh_Former")),

		        TagData.as("ns2:", "reportResponse/body/reportdata/participationscompany/propertyparticipant/activeparticipations/identificationnumber", MetaTag.valueOf("Pc_CrefoNr")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/participationscompany/propertyparticipant/activeparticipations/companyname", MetaTag.valueOf("Pc_Companyname")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/participationscompany/propertyparticipant/activeparticipations/tradename", MetaTag.valueOf("Pc_Tradename")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/participationscompany/propertyparticipant/activeparticipations/commercialname", MetaTag.valueOf("Pc_Commercialname")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/participationscompany/propertyparticipant/activeparticipations/alias", MetaTag.valueOf("Pc_Alias")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/participationscompany/propertyparticipant/activeparticipations/street", MetaTag.valueOf("Pc_Street")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/participationscompany/propertyparticipant/activeparticipations/housenumber", MetaTag.valueOf("Pc_HomeNr")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/participationscompany/propertyparticipant/activeparticipations/housenumberaffix", MetaTag.valueOf("Pc_AddressExtension")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/participationscompany/propertyparticipant/activeparticipations/postcode", MetaTag.valueOf("Pc_Zip")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/participationscompany/propertyparticipant/activeparticipations/city", MetaTag.valueOf("Pc_City")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/participationscompany/propertyparticipant/formerparticipations", MetaTag.valueOf("Pc_Formerparticipations")),

		        TagData.as("ns2:", "reportResponse/body/reportdata/statusreply/text", MetaTag.valueOf("StatusReplyTxt")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/foundation/text", MetaTag.valueOf("FoundationTxt")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/register/text", MetaTag.valueOf("RegisterTxt")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/participationscompany/text", MetaTag.valueOf("ParticipationsCompanyTxt")),

		        TagData.as("ns2:", "reportResponse/body/reportdata/businesspurpose", MetaTag.valueOf("BusinessPurpose")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/branch/text", MetaTag.valueOf("BranchTxt")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/staffcompany/stafftext", MetaTag.valueOf("StaffCompanyTxt")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/staffgroup/stafftext", MetaTag.valueOf("StaffGroupTxt")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/staffgroup/companyname", MetaTag.valueOf("StaffGroupCompanyname")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/turnovercompany/turnovertext", MetaTag.valueOf("TurnoverCompanyTxt")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/turnovergroup/turnovertext", MetaTag.valueOf("TurnoverGroupTxt")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/balancesheet/text", MetaTag.valueOf("BalanceSheetTxt")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/balancesheet/balance/assets/assetheader", MetaTag.valueOf("AssetHeader")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/balancesheet/balance/liabilities/liabilitiesheader", MetaTag.valueOf("LiabilitiesHeader")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/remarks", MetaTag.valueOf("Remarks")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/businessdevelopment/text", MetaTag.valueOf("BusinessDevelopmentTxt")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/updatenote", MetaTag.valueOf("UpdateNote")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/specificationofprofession", MetaTag.valueOf("SpecificationOfProfession")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/ancillaryinformation/text", MetaTag.valueOf("AncillaryInformationTxt")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/financialdata", MetaTag.valueOf("FinancialData")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/businessarea", MetaTag.valueOf("BusinessArea")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/importexport", MetaTag.valueOf("ImportExport")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/paymentmode/text", MetaTag.valueOf("PaymentModeTxt")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/creditopinion/text", MetaTag.valueOf("CreditOpinionTxt")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/supplement", MetaTag.valueOf("Supplement")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/negativefacts", MetaTag.valueOf("NegativeFact")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/locations", MetaTag.valueOf("Locations")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/financialstatusprivateperson", MetaTag.valueOf("FinancialStatusPrivatePerson")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/formerlocations", MetaTag.valueOf("FormerLocations")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/affiliatedgroup", MetaTag.valueOf("AffiliatedGroup")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/balancesheetgroup", MetaTag.valueOf("BalanceSheetGroup")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/profitslossesgroup", MetaTag.valueOf("ProfitslossesGroup")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/evaluationsfinanceindustry", MetaTag.valueOf("EvaluationsFinanceIndustry")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/balancesolvency", MetaTag.valueOf("BalanceSolvency")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/financialstatementfigures", MetaTag.valueOf("FinancialStatementFigures")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/financialstatementfiguresgroup", MetaTag.valueOf("FinancialStatementFiguresGroup")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/turnovercompanyrange", MetaTag.valueOf("TurnoverCompanyRange")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/staffcompanyrange", MetaTag.valueOf("StaffCompanyRange")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/paymentbehaviour", MetaTag.valueOf("PaymentBehaviour")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/financialinformation", MetaTag.valueOf("FinancialInformation")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/reporthints", MetaTag.valueOf("ReportHints")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/text", MetaTag.valueOf("Sh_ShareholderCapitalTxt")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/active/text", MetaTag.valueOf("Sh_CapacitiesShareholderActiveTxt")),
		        TagData.as("ns2:", "reportResponse/body/reportdata/shareholdercapital/capacitiesshareholder/text", MetaTag.valueOf("Sh_CapacitiesShareholderTxt")),


		};
	}

	@Override
	public MetaTag[] getDataTags() {
		return new MetaTag[] {
				MetaTag.valueOf("CrefoNr"), MetaTag.valueOf("Sh_CrefoNr"), MetaTag.valueOf("Pc_CrefoNr"), MetaTag.valueOf("ZIP"), MetaTag.valueOf("Sh_Zip"), MetaTag.valueOf("Pc_Zip")
		};
	}

	@Override
	public String getNewData(MetaTag tag, int at) {

		switch (tag.ordinal()) {

//			case Companyname:
//			return "Name_"+getData(MetaTag.CrefoNr);
//
//			case Street:
//			return "Stra\u00dfe_"+getData(MetaTag.CrefoNr);
//
//			case HomeNr:
//			return "10";
//
//			case AddressExtension:
//			return "A";
//
//			case ZIP:
//			return StringUtils.subStringLeft(getData(MetaTag.ZIP, "00"), 2) + StringUtils.subStringRight(getData(MetaTag.CrefoNr), 3);
//
//			case City:
//			return "City_"+getData(MetaTag.CrefoNr);
//
//			case Tradename:
//			return "tradename_"+getData(MetaTag.CrefoNr);
//
//			case Commercialname:
//			return "commercialname_"+getData(MetaTag.CrefoNr);
//
//			case Quarter:
//			return "";
//
//			case DiallingCode:
//			return "010";
//
//			case Phonenumber:
//			return "5544";
//
//			case ExtensionNo:
//			return "100";
//
//			case Email:
//			return "email_"+getData(MetaTag.CrefoNr);
//
//			case Website:
//			return "Website_"+getData(MetaTag.CrefoNr);
//
//			case Name1:
//			return "Vorname_"+getData(MetaTag.CrefoNr);
//
//			case Name2:
//			return "Nachname_"+getData(MetaTag.CrefoNr);
//
//			case Birthname:
//			return "Geburtsname_"+getData(MetaTag.CrefoNr);
//
//			case Nameaffix:
//			return "Namenszusatz_"+getData(MetaTag.CrefoNr);
//
//			case SurnameWidow:
//			return "";//empty
//
//			case SurnameBeforeDivorce:
//			return "";//empty
//
//			case Alias:
//			return "";//empty
//
//			case DateOfBirth:
//			return "1988-01-01";
//
//			case DateOfFoundation:
//			return "1990-01-01";
//
//			case DateOfFirstLegalForm:
//			return "1991-01-01";
//
//			case DateLegalForm:
//			return "1992-01-01";
//
//			case RegisterId:
//			return "Handelsregisternummer_"+getData(MetaTag.CrefoNr);
//
//			case Sh_Name1:
//			return "Vorname_Beteiligte_"+getData(MetaTag.Sh_CrefoNr, at);
//
//			case Sh_Name2:
//			return "Famname_Beteiligte_"+getData(MetaTag.Sh_CrefoNr, at);
//
//			case Sh_Birthname:
//			return "";
//
//			case Sh_DateOfBirth:
//			return "";
//
//			case Sh_Street:
//			return "Stra\u00dfe_Beteiligte_"+getData(MetaTag.Sh_CrefoNr, at);
//
//			case Sh_HomeNr:
//			return "11";
//
//			case Sh_AddressExtension:
//			return "B";
//
//			case Sh_Zip:
//			return StringUtils.subStringLeft(getData(MetaTag.Sh_Zip, at), 2) + StringUtils.subStringRight(getData(MetaTag.Sh_CrefoNr, at), 3);
//
//			case Sh_City:
//			return "Ort_Beteiligte_"+getData(MetaTag.Sh_CrefoNr, at);
//
//			case Sh_Quarter:
//			return "";
//
//			case Pc_Companyname:
//			return "Name_Beteiligung_"+getData(MetaTag.Pc_CrefoNr, at);
//
//			case Pc_Tradename:
//			return "";//empty
//
//			case Pc_Commercialname:
//			return "";//empty
//
//			case Pc_Alias:
//			return "";//empty
//
//			case Pc_Street:
//			return "Straﬂe_Beteiligung_"+getData(MetaTag.Pc_CrefoNr, at);
//
//			case Pc_HomeNr:
//			return "12";
//
//			case Pc_AddressExtension:
//			return "C";
//
//			case Pc_Zip:
//			return StringUtils.subStringLeft(getData(MetaTag.Pc_Zip, at), 2) + StringUtils.subStringRight(getData(MetaTag.Pc_CrefoNr, at), 3);
//
//			case Pc_City:
//			return "Ort_Beteiligung_"+getData(MetaTag.Pc_CrefoNr, at);

		default:
			return "XXX";
		}
	}


	@Override
	public ReplacementType getDataReplacementMode(MetaTag tag) {
		switch (tag.ordinal()) {

//		case CrefoNr:
//		case Boni:
//		case Boni2:
//		case CreationDateTime:
//		case TextAuskunft:
//		case InquirySendTimestamp:
//		case UserIdIns:
//		case AddendumDeadline:
//		case InquiryReferenceNr:
//		case PaymentForm:
//		case CreditJudgment:
//		case AnnualTurnover:
//		case AT_Currency:
//		case TotalAssets:
//		case CountryCode:
//		case LegalformPrimary:
//		case EmployeesCount:
//
//			return ReplacementType.Ignore;
//
//		case Requestdata:
//		case Taxdata:
//		case Pc_Formerparticipations:
//		case Sh_ComplCapShareHldr:
//		case Sh_Other:
//		case Sh_Former:
//		case ParticipationsPrivatePerson:
//		case ParticipationsParticipants:
//		case Rd_Banks:
//		case Rd_RealeStates:
//		case Rd_LegalDisclaimer:
//		case DeputyManagement:
//		case StatusReplyTxt:
//		case FoundationTxt:
//		case RegisterTxt:
//		case ParticipationsCompanyTxt:
//
//		case BusinessPurpose:
//		case BranchTxt:
//		case StaffCompanyTxt:
//		case StaffGroupTxt:
//		case StaffGroupCompanyname:
//		case TurnoverCompanyTxt:
//		case TurnoverGroupTxt:
//		case BalanceSheetTxt:
//		case AssetHeader:
//		case LiabilitiesHeader:
//		case Remarks:
//		case BusinessDevelopmentTxt:
//		case UpdateNote:
//		case SpecificationOfProfession:
//		case AncillaryInformationTxt:
//		case FinancialData:
//		case BusinessArea:
//		case ImportExport:
//		case PaymentModeTxt:
//		case CreditOpinionTxt:
//		case Supplement:
//		case NegativeFact:
//		case Locations:
//		case FinancialStatusPrivatePerson:
//		case FormerLocations:
//		case AffiliatedGroup:
//		case BalanceSheetGroup:
//		case ProfitslossesGroup:
//		case EvaluationsFinanceIndustry:
//		case BalanceSolvency:
//		case FinancialStatementFigures:
//		case FinancialStatementFiguresGroup:
//		case TurnoverCompanyRange:
//		case StaffCompanyRange:
//		case PaymentBehaviour:
//		case FinancialInformation:
//		case ReportHints:
//		case Sh_ShareholderCapitalTxt:
//		case Sh_CapacitiesShareholderActiveTxt:
//		case Sh_CapacitiesShareholderTxt:
//
//			return ReplacementType.RemoveBlock;

		default:
			return ReplacementType.OnlyText;//DEFAULT_MODE
		}

	}

	public ReportResponse getNewInstance(Map<String, MultiValue<String>> data) {
		return new ReportResponse(data);

	}


}
