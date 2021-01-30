export class CommonFunctions {
    public static getDateFromJDBCString(timeInput): Date {
        if (timeInput != null && timeInput != undefined) {
            let dateTimeString: string = timeInput.toString();
            dateTimeString = dateTimeString.substr(0, dateTimeString.length - 9) + 'Z';
            return new Date(dateTimeString);
        }
        return undefined;
    }

    public static REFRESH_PAGE_MESSAGE = ' Please refresh the page to reflect your changes.';
}