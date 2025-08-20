<!DOCTYPE html>
<html lang="ca">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Confirmació de comanda</title>
</head>
<body style="margin:0;padding:0;background:#f6f8fb;">
<table role="presentation" width="100%" cellpadding="0" cellspacing="0" style="background:#f6f8fb;">
    <tr>
        <td align="center" style="padding:24px;">
            <table role="presentation" width="100%" cellpadding="0" cellspacing="0"
                   style="max-width:640px;background:#ffffff;border-radius:12px;border:1px solid #eaeef3;">
                <tr>
                    <td style="padding:24px 24px 8px 24px;font-family:Arial,Helvetica,sans-serif;">
                        <div style="font-size:18px;line-height:26px;color:#0f172a;"><strong>Hola, ${firstName}👋</strong>
                        </div>
                        <div style="margin-top:8px;font-size:14px;line-height:22px;color:#475569;">
                            Gràcies per la teva compra! Hem rebut la teva comanda i l’estem preparant.
                        </div>
                        <div style="margin-top:12px;display:inline-block;padding:6px 10px;border:1px solid #e2e8f0;border-radius:999px;font-size:12px;color:#0f172a;background:#f8fafc;">
                            Núm. de comanda: <strong>${orderId}</strong> &nbsp;•&nbsp; Data: ${date}
                        </div>
                    </td>
                </tr>

                <!-- Resum d'articles -->
                <tr>
                    <td style="padding:8px 24px 0 24px;">
                        <div style="font-family:Arial,Helvetica,sans-serif;font-size:16px;color:#0f172a;font-weight:700;margin:8px 0 8px 0;">
                            Articles de la comanda
                        </div>
                        <table role="presentation" width="100%" cellpadding="0" cellspacing="0"
                               style="border-collapse:collapse;border:1px solid #eaeef3;border-radius:8px;overflow:hidden;">
                            <thead>
                            <tr>
                                <th align="left"
                                    style="padding:12px 12px;font-family:Arial,Helvetica,sans-serif;font-size:12px;color:#64748b;background:#f8fafc;border-bottom:1px solid #eaeef3;">
                                    Article
                                </th>
                                <th align="center"
                                    style="padding:12px;font-family:Arial,Helvetica,sans-serif;font-size:12px;color:#64748b;background:#f8fafc;border-bottom:1px solid #eaeef3;">
                                    Unitats
                                </th>
                                <th align="right"
                                    style="padding:12px;font-family:Arial,Helvetica,sans-serif;font-size:12px;color:#64748b;background:#f8fafc;border-bottom:1px solid #eaeef3;">
                                    Preu
                                </th>
                                <th align="right"
                                    style="padding:12px;font-family:Arial,Helvetica,sans-serif;font-size:12px;color:#64748b;background:#f8fafc;border-bottom:1px solid #eaeef3;">
                                    Total
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list items as item>
                                <tr>
                                    <td style="padding:12px;font-family:Arial,Helvetica,sans-serif;border-bottom:1px solid #eaeef3;">
                                        <div style="font-size:13px;color:#0f172a;font-weight:600;">${item.title}</div>
                                        <div style="display: flex;flex-direction: row;gap: 8px; align-items: center">
                                        <#if item.color??>
                                            <div class="details-colors-item"
                                                 style="display: flex;padding: 2px;align-items: center;width: 10px;height: 10px;aspect-ratio: 1/1;border-radius: 50px;background: ${item.color}">
                                            </div>
                                        </#if>
                                        <#if item.size??>
                                            <div style="font-size:12px;color:#64748b;margin-top:2px;">${item.size}</div>
                                        </#if>
                                        </div>
                                    </td>
                                    <td align="center"
                                        style="padding:12px;font-family:Arial,Helvetica,sans-serif;border-bottom:1px solid #eaeef3;">
                                        ${item.quantity}
                                    </td>
                                    <td align="right"
                                        style="padding:12px;font-family:Arial,Helvetica,sans-serif;border-bottom:1px solid #eaeef3;">
                                        ${item.price}
                                    </td>
                                    <td align="right"
                                        style="padding:12px;font-family:Arial,Helvetica,sans-serif;border-bottom:1px solid #eaeef3;">
                                        <strong>${item.total}</strong></td>
                                </tr>
                            </#list>

                            </tbody>
                        </table>
                    </td>
                </tr>

                <!-- Totalitzador -->
                <tr>
                    <td style="padding:12px 24px 0 24px;">
                        <table role="presentation" width="100%" cellpadding="0" cellspacing="0"
                               style="border-collapse:collapse;">
                            <tr>
                                <td style="width:60%%;"></td>
                                <td>
                                    <table role="presentation" width="100%" cellpadding="0" cellspacing="0"
                                           style="border-collapse:collapse;">
                                        <tr>
                                            <td style="padding:6px 12px;font-family:Arial,Helvetica,sans-serif;font-size:13px;color:#475569;">
                                                Subtotal
                                            </td>
                                            <td align="right"
                                                style="padding:6px 12px;font-family:Arial,Helvetica,sans-serif;font-size:13px;color:#0f172a;">
                                                <strong>${subtotal}</strong></td>
                                        </tr>
                                        <tr>
                                            <td style="padding:6px 12px;font-family:Arial,Helvetica,sans-serif;font-size:13px;color:#475569;">
                                                Enviament
                                            </td>
                                            <td align="right"
                                                style="padding:6px 12px;font-family:Arial,Helvetica,sans-serif;font-size:13px;color:#0f172a;">
                                                <strong>${shipment}</strong></td>
                                        </tr>
                                        <tr>
                                            <td style="padding:10px 12px;border-top:1px solid #eaeef3;font-family:Arial,Helvetica,sans-serif;font-size:14px;color:#0f172a;">
                                                <strong>Total</strong></td>
                                            <td align="right"
                                                style="padding:10px 12px;border-top:1px solid #eaeef3;font-family:Arial,Helvetica,sans-serif;font-size:16px;color:#0f172a;">
                                                <strong>${total}</strong></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <!-- Enviament -->
                <tr>
                    <td style="padding:16px 24px 8px 24px;">
                        <div style="font-family:Arial,Helvetica,sans-serif;font-size:16px;color:#0f172a;font-weight:700;margin:8px 0 8px 0;">
                            Informació d’enviament
                        </div>
                        <table role="presentation" width="100%" cellpadding="0" cellspacing="0"
                               style="border-collapse:collapse;border:1px solid #eaeef3;border-radius:8px;">
                            <tr>
                                <td style="padding:12px 16px;font-family:Arial,Helvetica,sans-serif;font-size:13px;color:#475569;">
                                    <div style="color:#0f172a;font-weight:600;margin-bottom:6px;">Destinatari</div>
                                    <div>Nom: ${recipientLastName}, ${recipientFirstName}</div>
                                    <div>Telèfon: ${recipientPhone}</div>
                                </td>
                            </tr>
                            <tr>
                                <td style="padding:12px 16px;border-top:1px solid #eaeef3;font-family:Arial,Helvetica,sans-serif;font-size:13px;color:#475569;">
                                    <div style="color:#0f172a;font-weight:600;margin-bottom:6px;">Mètode d’enviament
                                    </div>
                                    <div>
                                        <#if shipmentType == "POINT">
                                            Punt de Recollida:
                                            <#elseif shipmentType == "ADDRESS">
                                            Direcció:
                                        </#if>
                                        ${address}
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <!-- Peu -->
                <tr>
                    <td style="padding:8px 24px 24px 24px;font-family:Arial,Helvetica,sans-serif;font-size:13px;line-height:20px;color:#64748b;">
                        Si tens qualsevol dubte, respon aquest correu o contacta’ns. Gràcies per confiar en nosaltres!
                        <br/><br/>
                        — La Colla del Drac de Ribes.
                    </td>
                </tr>
            </table>

            <table role="presentation" width="100%" cellpadding="0" cellspacing="0" style="max-width:640px;">
                <tr>
                    <td style="padding:12px 24px 0 24px;text-align:center;font-family:Arial,Helvetica,sans-serif;font-size:11px;line-height:16px;color:#94a3b8;">
                        Aquest correu s’ha enviat automàticament. No responguis a aquest missatge si no és necessari.
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
