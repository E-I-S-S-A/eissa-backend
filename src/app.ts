import express, { Request, Response } from "express";
import messageRouter from "./message/routes";
import keepRouter from "./keep/routes";

const app = express();

app.use("/message", messageRouter);
app.use("/keep", keepRouter);

export default app;
