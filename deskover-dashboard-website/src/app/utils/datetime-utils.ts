export class DatetimeUtils {
  public static addHours(date: Date, hours: number): Date {
    return new Date(date.getTime() + hours * 3600 * 1000);
  }

  public static addMinutes(date: Date, minutes: number): Date {
    return new Date(date.getTime() + minutes * 60 * 1000);
  }

  public static addSeconds(date: Date, seconds: number): Date {
    return new Date(date.getTime() + seconds * 1000);
  }

  public static addDays(date: Date, days: number): Date {
    return new Date(date.getTime() + days * 24 * 3600 * 1000);
  }

  public static addMonths(date: Date, months: number): Date {
    return new Date(date.getTime() + months * 30 * 24 * 3600 * 1000);
  }

  public static addYears(date: Date, years: number): Date {
    return new Date(date.getTime() + years * 365 * 24 * 3600 * 1000);
  }

  public static isExpired(date: Date): boolean {
    return new Date(date).getTime() < new Date().getTime();
  }
}
