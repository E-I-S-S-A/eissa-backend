import express from "express";
import cellRouter from "./cellRoutes";
import sheetRouter from "./sheetRoutes";

const keepRouter = express.Router();

keepRouter.use("/cell", cellRouter);
keepRouter.use("/sheet", sheetRouter);

export default keepRouter;