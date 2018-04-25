import {Mask} from "./mask";

export class DocumentMasks {
    id: string;
    masks: Map<number, Mask>;

    constructor(id, masks) {
        this.id = id;
        this.masks = masks;
    }
}
