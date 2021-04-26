import Association from '@/models/management/Association';

export default class Item {
  id: number | null = null;
  content: string = '';
  connections: Association[] = [new Association(), new Association(), new Association()];

  constructor(jsonObj?: Item) {
    if (jsonObj) {
      this.id = jsonObj.id;
      this.content = jsonObj.content;
      this.connections = jsonObj.connections.map(
        (AssociationDto : Association) => {
            return new Association(AssociationDto);
        }
      );
    }
  }
} 
