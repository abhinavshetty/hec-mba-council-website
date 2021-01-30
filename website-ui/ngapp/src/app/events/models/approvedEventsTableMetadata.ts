export class ApprovedEventsTableMetadata {
    public static headerRow = [{ name: 'ID', field: 'id', show: false },
    { name: 'Event Name', field: 'eventName', show: true },
    { name: 'Event Type', field: 'eventType', show: true },
    { name: 'Host', field: 'host', show: false },
    { name: 'Event Description', field: 'eventDescription', show: false },
    { name: 'Partners', field: 'eventPartners', show: false },
    { name: 'Language', field: 'eventLanguage', show: false },
    { name: 'Address', field: 'eventAddress', show: true },
    { name: 'Max Target Audience', field: 'maxTargetAudience', show: true },
    { name: 'Ticket Price', field: 'ticketPrice', show: false },
    { name: 'Ticket Purchase Link', field: 'ticketPurchaseLink', show: false },
    { name: 'Registration Start Time', field: 'eventRegistrationStartTime', show: true },
    { name: 'Registration End Time', field: 'eventRegistrationEndTime', show: true },
    { name: 'Start Time', field: 'eventStartTime', show: true },
    { name: 'End Time', field: 'eventEndTime', show: true },
    { name: 'Date to share on official SM handles', field: 'dateToShare', show: true },

    { name: 'Facebook Link', field: 'facebookLink', show: false },
    { name: 'Instagram Link', field: 'instagramLink', show: false },
    { name: 'Twitter Link', field: 'twitterLink', show: false }];


}