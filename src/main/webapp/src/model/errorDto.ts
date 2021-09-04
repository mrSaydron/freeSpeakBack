export interface Error {
  detail?: string;
  entityName?: string;
  errorKey?: string;
  message?: string;
  path?: string;
  params?: string;
  status?: number;
  title?: string;
  type?: string;
}

export class ErrorDto implements Error {
  /* eslint no-useless-constructor: "off" */
  constructor(
    public detail?: string,
    public entityName?: string,
    public errorKey?: string,
    public message?: string,
    public path?: string,
    public params?: string,
    public status?: number,
    public title?: string,
    public type?: string
  ) {}
}
