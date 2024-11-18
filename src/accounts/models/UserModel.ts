import mongoose, { Schema } from "mongoose";
import { User } from "../interfaces/User";

const UserSchema: Schema<User> = new Schema(
    {
        firstName: { type: String, required: true },
        lastName: { type: String },
        email: { type: String, required: true },
        passwordHash: { type: String, required: true },
    },
    { timestamps: true }
);

export const UserModel = mongoose.model<User>("User", UserSchema);
