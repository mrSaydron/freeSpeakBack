export interface TextTag {
  id?: number;
  tagName?: string;
  typeName?: string;
  parent?: TextTagDto;
}

export class TextTagDto implements TextTag {
  /* eslint no-useless-constructor: "off" */
  constructor (
    public id?: number,
    public tagName?: string,
    public typeName?: string,
    public parent?: TextTagDto
  ) {}
}
