export interface IDictionary {
  id?: number;
  baseLanguage?: string;
  targerLanguage?: string;
}

export class Dictionary implements IDictionary {
  constructor(public id?: number, public baseLanguage?: string, public targerLanguage?: string) {}
}
