import { ObjectId } from "mongoose";

export interface User {
    userId: ObjectId;
    firstName: string;
    lastName: string;
    email: string;
    passwordHash: string;
    createdAt: Date;
}
