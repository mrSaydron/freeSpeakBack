export interface WordProgress {
  boxNumber?: number;
  successfulAttempts?: number;
  type?: string;
}

export class WordProgressDto implements WordProgress {
  constructor(
    public boxNumber?: number,
    public successfulAttempts?: number,
    public type?: string
  ) {}
}
