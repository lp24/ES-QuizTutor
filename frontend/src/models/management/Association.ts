export default class Association {
  itemOne: number | null = null;
  itemTwo: number | null = null;

  constructor(jsonObj?: Association) {
    if (jsonObj) {
      this.itemOne = jsonObj.itemOne;
	  this.itemTwo = jsonObj.itemTwo;
	}
  }
 }
