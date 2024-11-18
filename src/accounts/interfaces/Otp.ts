import { ObjectId } from "mongoose";

export interface Otp {
    otpId: string;
    userId: ObjectId;
    otpCode: string;
    expirationTime: Date;
    createdAt: Date;
    used: boolean; 
}
