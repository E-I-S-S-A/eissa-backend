import express from "express";
import authController from "../controllers/authController";

const authRouter = express.Router();

authRouter.route("/signup").post(authController.signup)