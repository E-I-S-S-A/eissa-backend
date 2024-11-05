import express from "express";
import chatRouter from "./chatRoutes";
import conversationRouter from "./conversationRoutes";

const messageRouter = express.Router();

messageRouter.use("/chat", chatRouter);
messageRouter.use("/conversation", conversationRouter);

export default messageRouter;