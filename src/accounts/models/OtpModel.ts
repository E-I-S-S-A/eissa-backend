import ObjectId, { Schema } from "mongoose";
import { Otp } from "../interfaces/Otp";
import mongoose from "mongoose";

const OtpSchema: Schema<Otp> = new Schema(
    {
        createdAt: { type: Date, required: true },
        expirationTime: { type: Date, required: true },
        otpCode: { type: String, required: true },
        used: { type: Boolean, required: true },
        userId: { type: ObjectId, required: true, ref: "User" },
    },
    { timestamps: true }
);

export const OtpModel = mongoose.model<Otp>("Otp", OtpSchema);
