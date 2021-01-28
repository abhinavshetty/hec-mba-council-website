export class EventWorkflow {
    workflowId: number;
    submitterUserName: string;
    reviewerUserName: string;
    reviewStatus: string;
    reviewComments:string;
    submissionTime: Date;
    reviewTime: Date;
    eventType: string;
	host: string;
	eventName: string;
	eventDescription: string;
	eventPartners: string;
	eventLanguage: string;
	eventPosterLoc: string;
	eventAddress: string;
	maxTargetAudience: number;
	ticketPrice: number;
	ticketPurchaseLink: string;
	
	/*
	 * Time related variables 
	 */
	eventStartTime: Date;
	eventRegistrationStartTime: Date;
	eventRegistrationEndTime: Date;
	eventEndTime: Date;
	dateToShare: Date;
	/*
	 * Social media links
	 */
	facebookLink: string;
	instagramLink: string;
	twitterLink: string;
}