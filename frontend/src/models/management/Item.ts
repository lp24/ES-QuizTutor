export default class Item {
  id: number | null = null;
  content: string = '';

  constructor(jsonObj?: Item) {
    if (jsonObj) {
      this.id = jsonObj.id;
      this.content = jsonObj.content;
        }
      );
 }
 }