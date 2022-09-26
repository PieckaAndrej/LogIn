import { createTheme } from "@mui/material/styles";
import variables from "./variables.module.scss"

export const lightTheme = createTheme({
  palette: {
    primary: {
      main: variables.primary,
    },
    secondary: {
      main: variables.secondary,
    },
  },
});
