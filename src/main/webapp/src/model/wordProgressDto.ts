export interface WordProgress {
  id?: number;
  boxNumber?: number;
  successfulAttempts?: number;
  type?: string;
}

export class WordProgressDto implements WordProgress {
  constructor(
    public id: number,
    public boxNumber?: number,
    public successfulAttempts?: number,
    public type?: string
  ) {}
}
