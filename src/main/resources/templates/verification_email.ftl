<!DOCTYPE html>
<html lang="ca">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Codi de verificació</title>
  </head>
  <body style="margin:0;padding:0;background:#f6f8fb;">
    <table role="presentation" width="100%" cellpadding="0" cellspacing="0" style="background:#f6f8fb;">
      <tr>
        <td align="center" style="padding:24px;">
          <table role="presentation" width="100%" cellpadding="0" cellspacing="0" style="max-width:560px;background:#ffffff;border-radius:12px;border:1px solid #eaeef3;">
            <tr>
              <td style="padding:24px 24px 8px 24px;font-family:Arial,Helvetica,sans-serif;font-size:18px;line-height:26px;color:#0f172a;">
                <strong>Hola, ${user} 👋</strong>
              </td>
            </tr>
            <tr>
              <td style="padding:0 24px 16px 24px;font-family:Arial,Helvetica,sans-serif;font-size:14px;line-height:22px;color:#475569;">
                Aquí tens el teu codi d’un sol ús per verificar el teu compte:
              </td>
            </tr>
            <tr>
              <td align="center" style="padding:8px 24px 24px 24px;">
                <div style="display:inline-block;padding:14px 24px;border:1px solid #e2e8f0;border-radius:8px;font-family:'SFMono-Regular',Consolas,'Liberation Mono',Menlo,monospace;font-size:28px;letter-spacing:4px;line-height:1;color:#0f172a;background:#f8fafc;">
                  ${totp}
                </div>
              </td>
            </tr>
            <tr>
              <td style="padding:0 24px 16px 24px;font-family:Arial,Helvetica,sans-serif;font-size:13px;line-height:20px;color:#64748b;">
                Introdueix aquest codi per continuar. Per seguretat, el codi caduca en pocs minuts i només es pot utilitzar una vegada.
              </td>
            </tr>
            <tr>
              <td style="padding:0 24px 16px 24px;font-family:Arial,Helvetica,sans-serif;font-size:13px;line-height:20px;color:#64748b;">
                Si no has sol·licitat aquest codi, pots ignorar aquest missatge.
              </td>
            </tr>
            <tr>
              <td style="padding:0 24px 24px 24px;font-family:Arial,Helvetica,sans-serif;font-size:13px;line-height:20px;color:#94a3b8;">
                Gràcies,<br/>La Colla del Drac de Ribes.
              </td>
            </tr>
          </table>
          <table role="presentation" width="100%" cellpadding="0" cellspacing="0" style="max-width:560px;">
            <tr>
              <td style="padding:12px 24px 0 24px;text-align:center;font-family:Arial,Helvetica,sans-serif;font-size:11px;line-height:16px;color:#94a3b8;">
                Aquest correu s’ha enviat automàticament. No responguis a aquest missatge.
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </body>
</html>
